<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Checkout - RaniyaMart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">🛍️ RaniyaMart</a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/cart" class="btn btn-secondary">&larr; Back to Cart</a></li>
        </ul>
    </nav>

    <div class="container">
        <h2>Secure Checkout & Payment Selection</h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger"><c:out value="${errorMessage}"/></div>
        </c:if>

        <div style="display: grid; grid-template-columns: 1.2fr 1fr; gap: 2rem; margin-top: 2rem;">
            <div class="table-container" style="padding: 1.5rem;">
                <form id="checkout-form" action="${pageContext.request.contextPath}/checkout" method="POST">
                    
                    <!-- Section 1: Delivery Address -->
                    <h3 style="margin-bottom: 1rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.5rem;">1. Shipping & Delivery Address</h3>
                    <div class="form-group">
                        <label>Shipping Address</label>
                        <input type="text" name="shippingAddress" class="search-input" value="123 Anna University Campus Road, Guindy, Chennai" required/>
                    </div>
                    <div class="form-group">
                        <label>Contact Phone Number</label>
                        <input type="text" name="contactPhone" class="search-input" value="+91 98765 43210" required/>
                    </div>

                    <!-- Section 2: Payment Module Selection -->
                    <h3 style="margin-top: 2rem; margin-bottom: 1rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.5rem;">2. Select Payment Method</h3>
                    
                    <div style="display: flex; gap: 0.75rem; margin-bottom: 1.5rem;">
                        <label style="flex: 1; padding: 0.8rem; background: var(--bg-surface); border: 2px solid var(--accent-color); border-radius: 8px; cursor: pointer; text-align: center;">
                            <input type="radio" name="paymentMethod" value="UPI" checked onclick="togglePaymentView('UPI')"/>
                            <strong style="display: block; margin-top: 0.2rem;">📱 UPI Payment</strong>
                            <small style="color: var(--text-secondary);">GPay, PhonePe, Paytm</small>
                        </label>

                        <label style="flex: 1; padding: 0.8rem; background: var(--bg-surface); border: 2px solid var(--border-color); border-radius: 8px; cursor: pointer; text-align: center;">
                            <input type="radio" name="paymentMethod" value="CARD" onclick="togglePaymentView('CARD')"/>
                            <strong style="display: block; margin-top: 0.2rem;">💳 Credit / Debit Card</strong>
                            <small style="color: var(--text-secondary);">Visa, MasterCard, RuPay</small>
                        </label>

                        <label style="flex: 1; padding: 0.8rem; background: var(--bg-surface); border: 2px solid var(--border-color); border-radius: 8px; cursor: pointer; text-align: center;">
                            <input type="radio" name="paymentMethod" value="COD" onclick="togglePaymentView('COD')"/>
                            <strong style="display: block; margin-top: 0.2rem;">💵 Cash on Delivery</strong>
                            <small style="color: var(--text-secondary);">Pay at doorstep</small>
                        </label>
                    </div>

                    <!-- Dynamic Payment Panel 1: UPI -->
                    <div id="payment-panel-upi" style="background: var(--bg-surface); padding: 1.25rem; border-radius: 10px; margin-bottom: 1.5rem;">
                        <div class="form-group">
                            <label>Select UPI Application</label>
                            <select name="upiApp" class="select-input">
                                <option value="Google Pay">Google Pay (GPay)</option>
                                <option value="PhonePe">PhonePe</option>
                                <option value="Paytm">Paytm</option>
                                <option value="BHIM UPI">BHIM UPI</option>
                            </select>
                        </div>
                        <div class="form-group" style="margin-bottom: 0;">
                            <label>Virtual Payment Address / UPI ID</label>
                            <input type="text" name="upiId" class="search-input" value="buyer@upi" placeholder="username@upi"/>
                        </div>
                    </div>

                    <!-- Dynamic Payment Panel 2: Credit / Debit Card -->
                    <div id="payment-panel-card" style="background: var(--bg-surface); padding: 1.25rem; border-radius: 10px; margin-bottom: 1.5rem; display: none;">
                        <div class="form-group">
                            <label>Cardholder Name</label>
                            <input type="text" name="cardHolder" class="search-input" value="Jane Buyer"/>
                        </div>
                        <div class="form-group">
                            <label>Card Number</label>
                            <input type="text" name="cardNumber" class="search-input" value="4532 •••• •••• 8892" placeholder="4532 0000 0000 0000" maxlength="19"/>
                        </div>
                        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1rem;">
                            <div class="form-group" style="margin-bottom: 0;">
                                <label>Expiry Date (MM/YY)</label>
                                <input type="text" name="cardExpiry" class="search-input" value="12/28" placeholder="MM/YY"/>
                            </div>
                            <div class="form-group" style="margin-bottom: 0;">
                                <label>CVV Security Code</label>
                                <input type="password" name="cardCvv" class="search-input" value="123" maxlength="4" placeholder="123"/>
                            </div>
                        </div>
                    </div>

                    <!-- Dynamic Payment Panel 3: Cash on Delivery -->
                    <div id="payment-panel-cod" style="background: var(--bg-surface); padding: 1.25rem; border-radius: 10px; margin-bottom: 1.5rem; display: none;">
                        <div style="display: flex; gap: 0.75rem; align-items: center;">
                            <span style="font-size: 2rem;">🚚</span>
                            <div>
                                <strong>Cash on Delivery Eligible</strong>
                                <p style="font-size: 0.88rem; color: var(--text-secondary); margin-top: 0.2rem;">Pay in Cash or scan QR code via UPI directly to our delivery executive when your parcel arrives.</p>
                            </div>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 1rem; padding: 0.9rem; font-size: 1.15rem; font-weight: 700;">Place Order Now (₹<c:out value="${cartTotal}"/>)</button>
                </form>
            </div>

            <!-- Order Summary -->
            <div class="table-container" style="padding: 1.5rem; height: fit-content;">
                <h3 style="margin-bottom: 1rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.5rem;">Order Summary</h3>
                <c:forEach var="item" items="${cartItems}">
                    <div style="display: flex; justify-content: space-between; margin-bottom: 0.75rem; padding-bottom: 0.75rem; border-bottom: 1px solid var(--border-color);">
                        <div style="display: flex; gap: 0.75rem; align-items: center;">
                            <img src="${fn:escapeXml(item.product.imageUrl)}" alt="${fn:escapeXml(item.product.name)}" style="width: 48px; height: 48px; object-fit: cover; border-radius: 6px;" onerror="this.src='https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?auto=format&fit=crop&w=600&q=80';"/>
                            <div>
                                <strong><c:out value="${item.product.name}"/></strong>
                                <div style="font-size: 0.85rem; color: var(--text-secondary);">Qty: <c:out value="${item.quantity}"/> x ₹<c:out value="${item.product.price}"/></div>
                            </div>
                        </div>
                        <div style="font-weight: 700; color: var(--success-color); font-size: 1.1rem;">₹<c:out value="${item.product.price * item.quantity}"/></div>
                    </div>
                </c:forEach>
                
                <div style="display: flex; justify-content: space-between; font-size: 1.3rem; font-weight: 800; margin-top: 1.5rem; padding-top: 1rem; border-top: 2px dashed var(--border-color);">
                    <span>Grand Total:</span>
                    <span style="color: var(--success-color);">₹<c:out value="${cartTotal}"/></span>
                </div>
            </div>
        </div>
    </div>

    <script>
        function togglePaymentView(method) {
            document.getElementById('payment-panel-upi').style.display = (method === 'UPI') ? 'block' : 'none';
            document.getElementById('payment-panel-card').style.display = (method === 'CARD') ? 'block' : 'none';
            document.getElementById('payment-panel-cod').style.display = (method === 'COD') ? 'block' : 'none';
        }
    </script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script src="${pageContext.request.contextPath}/js/chatbot.js"></script>
</body>
</html>
