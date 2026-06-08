const API_URL = "http://localhost:8080/api";

let isTamil = false;
let currentUser = JSON.parse(localStorage.getItem("currentUser") || "null");
let authToken = localStorage.getItem("authToken") || "";
let cart = [];
let products = [];
let activeCategory = "all";

const fallbackProducts = [
    { id: 1, name: "Rice", tamilName: "அரிசி", category: "grains", price: 50, unit: "kg", description: "Premium quality rice from local farms", tamilDescription: "உள்ளூர் பண்ணைகளில் இருந்து கிடைக்கும் தரமான அரிசி", image: "images/rice.jpg", stock: 100 },
    { id: 2, name: "Dhal", tamilName: "பருப்பு", category: "grains", price: 120, unit: "kg", description: "Nutritious lentils for everyday meals", tamilDescription: "சத்தான அன்றாட உணவுக்கான பருப்பு", image: "images/dhal.jpg", stock: 100 },
    { id: 3, name: "Cooking Oil", tamilName: "சமையல் எண்ணெய்", category: "oils", price: 150, unit: "liter", description: "Pure cooking oil for home cooking", tamilDescription: "வீட்டு சமையலுக்கான தூய எண்ணெய்", image: "images/Oil.jpg", stock: 80 },
    { id: 4, name: "Spice Powders", tamilName: "மசாலா தூள்", category: "spices", price: 80, unit: "packet", description: "Authentic spice mix for traditional flavors", tamilDescription: "பாரம்பரிய சுவைக்கான அசல் மசாலா கலவை", image: "images/powders.jpg", stock: 90 },
    { id: 5, name: "Cosmetics", tamilName: "அழகு பொருட்கள்", category: "cosmetics", price: 200, unit: "set", description: "Daily-use beauty and personal care products", tamilDescription: "தினசரி பயன்பாட்டிற்கான அழகு மற்றும் பராமரிப்பு பொருட்கள்", image: "images/cosmetics.jpg", stock: 60 },
    { id: 6, name: "Wheat Flour", tamilName: "கோதுமை மாவு", category: "grains", price: 40, unit: "kg", description: "Finely ground wheat flour", tamilDescription: "நன்றாக அரைக்கப்பட்ட கோதுமை மாவு", image: "../images/wheat flour.png", stock: 100 },
    { id: 7, name: "Sugar", tamilName: "சர்க்கரை", category: "grains", price: 45, unit: "kg", description: "Pure white sugar", tamilDescription: "தூய வெள்ளை சர்க்கரை", image: "../images/sugar.png", stock: 100 },
    { id: 8, name: "Tea Powder", tamilName: "தேயிலை தூள்", category: "beverages", price: 180, unit: "packet", description: "Premium tea powder for fresh tea", tamilDescription: "சுவையான தேநீருக்கான தரமான தேயிலை தூள்", image: "../images/tea.png", stock: 75 },
    { id: 9, name: "Milk", tamilName: "பால்", category: "dairy", price: 60, unit: "liter", description: "Fresh packed milk", tamilDescription: "புதிய பாக்கெட் பால்", image: "../images/milk.png", stock: 50 },
    { id: 10, name: "Butter", tamilName: "வெண்ணெய்", category: "dairy", price: 250, unit: "packet", description: "Pure butter for cooking and baking", tamilDescription: "சமையல் மற்றும் பேக்கிங்கிற்கான தூய வெண்ணெய்", image: "../images/butter.png", stock: 40 },
    { id: 11, name: "Cheese", tamilName: "சீஸ்", category: "dairy", price: 300, unit: "packet", description: "Processed cheese slices", tamilDescription: "செயலாக்கப்பட்ட சீஸ் துண்டுகள்", image: "../images/cheese.png", stock: 35 },
    { id: 12, name: "Bread", tamilName: "ரொட்டி", category: "bakery", price: 35, unit: "loaf", description: "Fresh baked bread", tamilDescription: "புதியதாக சுடப்பட்ட ரொட்டி", image: "../images/bread.png", stock: 45 },
    { id: 13, name: "Eggs", tamilName: "முட்டை", category: "dairy", price: 6, unit: "piece", description: "Farm fresh eggs", tamilDescription: "பண்ணையில் இருந்து கிடைக்கும் புதிய முட்டைகள்", image: "../images/eggs.png", stock: 120 },
    { id: 14, name: "Potatoes", tamilName: "உருளைக்கிழங்கு", category: "vegetables", price: 25, unit: "kg", description: "Fresh potatoes", tamilDescription: "புதிய உருளைக்கிழங்கு", image: "../images/potatoes.png", stock: 100 },
    { id: 15, name: "Onions", tamilName: "வெங்காயம்", category: "vegetables", price: 30, unit: "kg", description: "Red onions", tamilDescription: "சிவப்பு வெங்காயம்", image: "../images/onion.png", stock: 100 },
    { id: 16, name: "Tomatoes", tamilName: "தக்காளி", category: "vegetables", price: 35, unit: "kg", description: "Ripe tomatoes", tamilDescription: "பழுத்த தக்காளி", image: "../images/tomatoes.png", stock: 100 }
];

document.addEventListener("DOMContentLoaded", async function () {
    loadCart();
    updateUserUI();
    await loadProducts();
    updateCartCount();
});

async function apiRequest(path, options = {}) {
    const headers = { ...(options.headers || {}) };

    if (options.body) {
        headers["Content-Type"] = "application/json";
    }

    if (authToken) {
        headers.Authorization = `Bearer ${authToken}`;
    }

    const response = await fetch(`${API_URL}${path}`, { ...options, headers });
    const data = await response.json().catch(() => null);

    if (!response.ok) {
        throw new Error(data?.message || `Request failed with status ${response.status}`);
    }

    return data;
}

async function loadProducts() {
    const status = document.getElementById("apiStatus");
    try {
        products = await apiRequest("/products", { method: "GET" });
        status.textContent = "";
    } catch (error) {
        products = fallbackProducts;
        status.textContent = "Backend is offline, showing demo products.";
    }
    displayProducts(getVisibleProducts());
}

function getVisibleProducts() {
    const searchTerm = document.getElementById("searchInput").value.trim().toLowerCase();
    return products.filter(product => {
        const name = (isTamil ? product.tamilName : product.name).toLowerCase();
        const categoryMatch = activeCategory === "all" || product.category === activeCategory;
        const searchMatch = !searchTerm || name.includes(searchTerm) || product.category.toLowerCase().includes(searchTerm);
        return categoryMatch && searchMatch;
    });
}

function displayProducts(productList) {
    const grid = document.getElementById("productGrid");
    grid.innerHTML = "";

    if (productList.length === 0) {
        grid.innerHTML = `<p class="empty-state">No products found.</p>`;
        return;
    }

    productList.forEach(product => grid.appendChild(createProductCard(product)));
}

function createProductCard(product) {
    const card = document.createElement("div");
    card.className = "card";
    card.onclick = () => showProductDetails(product.id);

    card.innerHTML = `
        <img src="${product.image}" alt="${product.name}">
        <div class="card-body">
            <h3>${isTamil ? product.tamilName : product.name}</h3>
            <p>${formatCurrency(product.price)} per ${product.unit}</p>
            <span>${product.stock ?? 0} in stock</span>
            <button class="add-to-cart" onclick="event.stopPropagation(); addToCart(${product.id})">Add to Cart</button>
        </div>
    `;

    return card;
}

function showProductDetails(productId) {
    const product = products.find(p => Number(p.id) === Number(productId));
    if (!product) return;

    document.getElementById("productDetails").innerHTML = `
        <img src="${product.image}" alt="${product.name}">
        <h2>${isTamil ? product.tamilName : product.name}</h2>
        <p><strong>Price:</strong> ${formatCurrency(product.price)} per ${product.unit}</p>
        <p><strong>Stock:</strong> ${product.stock ?? 0}</p>
        <p>${isTamil ? product.tamilDescription : product.description}</p>
        <button class="add-to-cart" onclick="addToCart(${product.id}); closeModal()">Add to Cart</button>
    `;

    document.getElementById("productModal").style.display = "block";
}

function closeModal() {
    document.getElementById("productModal").style.display = "none";
}

function addToCart(productId) {
    const product = products.find(p => Number(p.id) === Number(productId));
    if (!product) return;

    const existingItem = cart.find(item => Number(item.id) === Number(productId));
    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        cart.push({ ...product, quantity: 1 });
    }

    saveCart();
    updateCartCount();
    showMessage("cartMessage", `${product.name} added to cart.`);
}

function showCart() {
    const items = document.getElementById("cartItems");
    const total = document.getElementById("cartTotal");
    items.innerHTML = "";

    if (cart.length === 0) {
        items.innerHTML = `<p class="empty-state">Your cart is empty.</p>`;
        total.innerHTML = "";
    } else {
        cart.forEach(item => {
            const itemTotal = item.price * item.quantity;
            const itemDiv = document.createElement("div");
            itemDiv.className = "cart-item";
            itemDiv.innerHTML = `
                <img src="${item.image}" alt="${item.name}">
                <div>
                    <h4>${isTamil ? item.tamilName : item.name}</h4>
                    <p>${formatCurrency(item.price)} x ${item.quantity} = ${formatCurrency(itemTotal)}</p>
                </div>
                <button onclick="removeFromCart(${item.id})">Remove</button>
            `;
            items.appendChild(itemDiv);
        });
        total.innerHTML = `<h3>Total: ${formatCurrency(getCartTotal())}</h3>`;
    }

    document.getElementById("cartModal").style.display = "block";
}

function closeCartModal() {
    document.getElementById("cartModal").style.display = "none";
}

function removeFromCart(productId) {
    cart = cart.filter(item => Number(item.id) !== Number(productId));
    saveCart();
    updateCartCount();
    showCart();
}

function checkout() {
    if (!currentUser || !authToken) {
        closeCartModal();
        showLoginModal();
        showMessage("loginMessage", "Please login before checkout.");
        return;
    }

    if (cart.length === 0) {
        showMessage("cartMessage", "Your cart is empty.");
        return;
    }

    showOrderModal();
}

function showOrderModal() {
    document.getElementById("orderModal").style.display = "block";
}

function closeOrderModal() {
    document.getElementById("orderModal").style.display = "none";
}

async function placeOrder(event) {
    event.preventDefault();
    const order = {
        items: cart.map(item => ({
            productId: item.id,
            quantity: item.quantity,
            price: item.price
        })),
        totalAmount: getCartTotal(),
        deliveryAddress: document.getElementById("deliveryAddress").value.trim(),
        deliveryCity: document.getElementById("deliveryCity").value.trim(),
        deliveryPostalCode: document.getElementById("deliveryPostalCode").value.trim(),
        notes: document.getElementById("orderNotes").value.trim()
    };

    try {
        const savedOrder = await apiRequest(`/orders?userId=${currentUser.id}`, {
            method: "POST",
            body: JSON.stringify(order)
        });

        cart = [];
        saveCart();
        updateCartCount();
        closeOrderModal();
        closeCartModal();
        alert(`Order #${savedOrder.id} placed successfully.`);
    } catch (error) {
        showMessage("orderMessage", error.message || "Unable to place order.");
    }
}

function saveCart() {
    localStorage.setItem("cart", JSON.stringify(cart));
}

function loadCart() {
    cart = JSON.parse(localStorage.getItem("cart") || "[]");
}

function filterProducts() {
    displayProducts(getVisibleProducts());
}

function filterByCategory(event, category) {
    activeCategory = category;
    document.querySelectorAll(".filters button").forEach(button => button.classList.remove("active"));
    event.target.classList.add("active");
    displayProducts(getVisibleProducts());
}

function showLoginModal() {
    document.getElementById("loginModal").style.display = "block";
    showLogin();
}

function closeLoginModal() {
    document.getElementById("loginModal").style.display = "none";
}

function showLogin() {
    document.getElementById("loginForm").style.display = "block";
    document.getElementById("signupForm").style.display = "none";
}

function showSignup() {
    document.getElementById("loginForm").style.display = "none";
    document.getElementById("signupForm").style.display = "block";
}

async function login(event) {
    event.preventDefault();
    try {
        const response = await apiRequest("/auth/login", {
            method: "POST",
            body: JSON.stringify({
                email: document.getElementById("email").value.trim(),
                password: document.getElementById("password").value
            })
        });
        completeAuth(response);
        closeLoginModal();
    } catch (error) {
        showMessage("loginMessage", error.message || "Login failed.");
    }
}

async function signup(event) {
    event.preventDefault();
    try {
        const response = await apiRequest("/auth/signup", {
            method: "POST",
            body: JSON.stringify({
                name: document.getElementById("name").value.trim(),
                email: document.getElementById("signupEmail").value.trim(),
                password: document.getElementById("signupPassword").value,
                phone: document.getElementById("phone").value.trim()
            })
        });
        completeAuth(response);
        closeLoginModal();
    } catch (error) {
        showMessage("signupMessage", error.message || "Signup failed.");
    }
}

function completeAuth(response) {
    if (!response.token || !response.user) {
        throw new Error(response.message || "Authentication failed.");
    }

    authToken = response.token;
    currentUser = response.user;
    localStorage.setItem("authToken", authToken);
    localStorage.setItem("currentUser", JSON.stringify(currentUser));
    updateUserUI();
}

function updateUserUI() {
    const controls = document.querySelector(".header-controls");
    if (currentUser) {
        controls.innerHTML = `
            <button onclick="toggleLanguage()">தமிழ் / English</button>
            <span class="welcome">Welcome, ${currentUser.name}</span>
            <button onclick="logout()">Logout</button>
            <button onclick="showCart()">Cart (<span id="cartCount">0</span>)</button>
        `;
    } else {
        controls.innerHTML = `
            <button onclick="toggleLanguage()">தமிழ் / English</button>
            <button id="loginButton" onclick="showLoginModal()">Login</button>
            <button onclick="showCart()">Cart (<span id="cartCount">0</span>)</button>
        `;
    }
    updateCartCount();
}

function logout() {
    currentUser = null;
    authToken = "";
    localStorage.removeItem("authToken");
    localStorage.removeItem("currentUser");
    updateUserUI();
}

function updateCartCount() {
    const count = cart.reduce((total, item) => total + item.quantity, 0);
    const countElement = document.getElementById("cartCount");
    if (countElement) {
        countElement.textContent = count;
    }
}

function toggleLanguage() {
    isTamil = !isTamil;

    document.getElementById("storeName").innerText = isTamil ? "ஸ்ரீ தேவ் பாலகிருஷ்ணா ஸ்டோர்" : "Shri Dev BalaKrishna Store";
    document.getElementById("tagline").innerText = isTamil ? "உங்கள் நம்பகமான மளிகை கடை" : "Your Trusted Grocery Store";
    document.getElementById("service").innerText = isTamil ? "மொத்தமும் சில்லறையும் கிடைக்கும்" : "Wholesale & Retail Available";
    document.getElementById("heroCopy").innerText = isTamil ? "ஈரோட்டில் புதிய மளிகை பொருட்கள் மற்றும் அன்பான உள்ளூர் சேவை." : "Fresh groceries, daily essentials, and friendly local service in Erode.";
    document.getElementById("productTitle").innerText = isTamil ? "எங்கள் பொருட்கள்" : "Our Products";
    document.getElementById("aboutTitle").innerText = isTamil ? "எங்களை பற்றி" : "About Us";
    document.getElementById("contactTitle").innerText = isTamil ? "தொடர்பு விவரங்கள்" : "Contact Details";
    document.getElementById("address").innerHTML = isTamil ? "<b>முகவரி:</b> ஈரோடு, தமிழ்நாடு" : "<b>Address:</b> Erode, Tamil Nadu";
    document.getElementById("aboutText").innerText = isTamil
        ? "ஈரோடு, தமிழ்நாட்டில் உள்ள எங்கள் நம்பகமான மளிகை கடைக்கு வரவேற்கிறோம். அரிசி, பருப்பு, எண்ணெய், மசாலா, பால் பொருட்கள், காய்கறிகள் மற்றும் அன்றாட தேவைகள் மொத்த மற்றும் சில்லறை வாடிக்கையாளர்களுக்கு கிடைக்கும்."
        : "Welcome to Shri Dev BalaKrishna Store, your trusted source for quality groceries in Erode, Tamil Nadu. We offer rice, dhal, oils, spices, dairy, vegetables, bakery items, and everyday essentials for wholesale and retail customers.";

    displayProducts(getVisibleProducts());
    updateUserUI();
}

function orderWhatsApp() {
    const message = "Hello, I want to place an order from Shri Dev BalaKrishna Store";
    window.open(`https://wa.me/919994411370?text=${encodeURIComponent(message)}`, "_blank");
}

function getCartTotal() {
    return cart.reduce((total, item) => total + item.price * item.quantity, 0);
}

function formatCurrency(value) {
    return `Rs. ${Number(value || 0).toFixed(2)}`;
}

function showMessage(elementId, message) {
    const element = document.getElementById(elementId);
    if (element) {
        element.textContent = message;
    }
}
