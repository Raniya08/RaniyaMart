/**
 * RaniyaMart Enhanced Floating AI Chatbot Widget Component
 */
(function () {
    document.addEventListener('DOMContentLoaded', function () {
        // Inject Floating Widget HTML
        const widgetHTML = `
            <div id="rm-chat-widget">
                <button id="rm-chat-btn" title="Ask RaniyaMart Assistant">
                    💬 <span class="rm-chat-badge">AI</span>
                </button>
                <div id="rm-chat-panel" class="rm-hidden">
                    <div class="rm-chat-header">
                        <div class="rm-chat-title">
                            <span>🤖 RaniyaMart AI Assistant</span>
                            <small>24/7 E-Commerce Shopping & Order Helper</small>
                        </div>
                        <div class="rm-header-actions">
                            <button id="rm-chat-clear" title="Clear Conversation">🧹</button>
                            <button id="rm-chat-close" title="Close Panel">&times;</button>
                        </div>
                    </div>
                    <div class="rm-chat-body" id="rm-chat-messages">
                        <div class="rm-msg rm-msg-bot">
                            <div class="rm-msg-content">👋 Hello! I'm your RaniyaMart AI Assistant. How can I help you today?</div>
                            <span class="rm-msg-time">${getTime()}</span>
                        </div>
                        <div class="rm-faq-pills">
                            <button class="rm-faq-pill" data-query="Shipping Info">🚚 Shipping</button>
                            <button class="rm-faq-pill" data-query="How to track order">📦 Order Status</button>
                            <button class="rm-faq-pill" data-query="Return policy">🔄 Returns</button>
                            <button class="rm-faq-pill" data-query="Payment options">💳 Payment</button>
                            <button class="rm-faq-pill" data-query="Customer support">📞 Support</button>
                        </div>
                    </div>
                    <div class="rm-chat-footer">
                        <input type="text" id="rm-chat-input" placeholder="Ask about shipping, orders, returns..." maxlength="500"/>
                        <button id="rm-chat-send">Send</button>
                    </div>
                </div>
            </div>
        `;

        const container = document.createElement('div');
        container.innerHTML = widgetHTML;
        document.body.appendChild(container);

        // Inject Widget Styles
        const style = document.createElement('style');
        style.textContent = `
            #rm-chat-widget {
                position: fixed;
                bottom: 24px;
                right: 24px;
                z-index: 99999;
                font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
            }
            #rm-chat-btn {
                background: linear-gradient(135deg, #6366f1, #4f46e5);
                color: #ffffff;
                border: none;
                border-radius: 50px;
                padding: 14px 22px;
                font-size: 16px;
                font-weight: 700;
                cursor: pointer;
                box-shadow: 0 8px 24px rgba(79, 70, 229, 0.4);
                display: flex;
                align-items: center;
                gap: 8px;
                transition: transform 0.2s ease, box-shadow 0.2s ease;
            }
            #rm-chat-btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 12px 28px rgba(79, 70, 229, 0.55);
            }
            .rm-chat-badge {
                background: #ef4444;
                color: #fff;
                font-size: 10px;
                padding: 2px 6px;
                border-radius: 10px;
                text-transform: uppercase;
            }
            #rm-chat-panel {
                position: absolute;
                bottom: 70px;
                right: 0;
                width: 380px;
                height: 520px;
                background: #1e1e2e;
                color: #cdd6f4;
                border: 1px solid rgba(255, 255, 255, 0.12);
                border-radius: 18px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.5);
                display: flex;
                flex-direction: column;
                overflow: hidden;
                transition: all 0.3s ease;
            }
            #rm-chat-panel.rm-hidden {
                display: none;
            }
            .rm-chat-header {
                background: #181825;
                padding: 14px 18px;
                border-bottom: 1px solid rgba(255, 255, 255, 0.08);
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .rm-chat-title span {
                display: block;
                font-size: 15px;
                font-weight: 700;
                color: #cdd6f4;
            }
            .rm-chat-title small {
                font-size: 11px;
                color: #a6adc8;
            }
            .rm-header-actions {
                display: flex;
                gap: 8px;
                align-items: center;
            }
            #rm-chat-clear, #rm-chat-close {
                background: transparent;
                border: none;
                color: #a6adc8;
                font-size: 16px;
                cursor: pointer;
                padding: 4px;
                border-radius: 4px;
                transition: color 0.2s;
            }
            #rm-chat-clear:hover, #rm-chat-close:hover {
                color: #ffffff;
            }
            .rm-chat-body {
                flex: 1;
                padding: 16px;
                overflow-y: auto;
                display: flex;
                flex-direction: column;
                gap: 12px;
            }
            .rm-msg {
                max-width: 85%;
                padding: 12px 16px;
                border-radius: 14px;
                font-size: 13px;
                line-height: 1.55;
                display: flex;
                flex-direction: column;
            }
            .rm-msg-bot {
                background: #313244;
                color: #cdd6f4;
                align-self: flex-start;
                border-bottom-left-radius: 4px;
            }
            .rm-msg-user {
                background: #6366f1;
                color: #ffffff;
                align-self: flex-end;
                border-bottom-right-radius: 4px;
            }
            .rm-msg-time {
                font-size: 10px;
                color: rgba(255, 255, 255, 0.45);
                margin-top: 6px;
                align-self: flex-end;
            }
            .rm-faq-pills {
                display: flex;
                flex-wrap: wrap;
                gap: 6px;
                margin-top: 4px;
            }
            .rm-faq-pill {
                background: rgba(99, 102, 241, 0.18);
                color: #a5b4fc;
                border: 1px solid rgba(99, 102, 241, 0.35);
                border-radius: 20px;
                padding: 6px 12px;
                font-size: 11px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.2s ease;
            }
            .rm-faq-pill:hover {
                background: rgba(99, 102, 241, 0.4);
                color: #ffffff;
            }
            .rm-typing-dots {
                display: flex;
                gap: 4px;
                padding: 4px 0;
            }
            .rm-typing-dot {
                width: 6px;
                height: 6px;
                background: #a6adc8;
                border-radius: 50%;
                animation: rmTyping 1.4s infinite ease-in-out;
            }
            .rm-typing-dot:nth-child(2) { animation-delay: 0.2s; }
            .rm-typing-dot:nth-child(3) { animation-delay: 0.4s; }
            @keyframes rmTyping {
                0%, 80%, 100% { transform: scale(0.6); opacity: 0.4; }
                40% { transform: scale(1); opacity: 1; }
            }
            .rm-chat-footer {
                padding: 12px;
                background: #181825;
                border-top: 1px solid rgba(255, 255, 255, 0.08);
                display: flex;
                gap: 8px;
            }
            #rm-chat-input {
                flex: 1;
                background: #313244;
                border: 1px solid rgba(255, 255, 255, 0.12);
                border-radius: 8px;
                padding: 10px 14px;
                color: #cdd6f4;
                font-size: 13px;
                outline: none;
            }
            #rm-chat-input:focus {
                border-color: #6366f1;
            }
            #rm-chat-send {
                background: #6366f1;
                color: #fff;
                border: none;
                border-radius: 8px;
                padding: 10px 16px;
                font-size: 13px;
                font-weight: 600;
                cursor: pointer;
            }
            #rm-chat-send:hover {
                background: #4f46e5;
            }
        `;
        document.head.appendChild(style);

        // Elements
        const chatBtn = document.getElementById('rm-chat-btn');
        const chatPanel = document.getElementById('rm-chat-panel');
        const chatClose = document.getElementById('rm-chat-close');
        const chatClear = document.getElementById('rm-chat-clear');
        const chatInput = document.getElementById('rm-chat-input');
        const chatSend = document.getElementById('rm-chat-send');
        const chatMessages = document.getElementById('rm-chat-messages');

        // Context Path
        const contextPath = window.location.pathname.startsWith('/RaniyaMart') ? '/RaniyaMart' : '';

        // Toggle Panel
        chatBtn.addEventListener('click', function () {
            chatPanel.classList.toggle('rm-hidden');
            if (!chatPanel.classList.contains('rm-hidden')) {
                chatInput.focus();
            }
        });

        chatClose.addEventListener('click', function () {
            chatPanel.classList.add('rm-hidden');
        });

        // Clear Chat
        chatClear.addEventListener('click', function () {
            chatMessages.innerHTML = `
                <div class="rm-msg rm-msg-bot">
                    <div class="rm-msg-content">👋 Chat cleared! How can I help you with RaniyaMart?</div>
                    <span class="rm-msg-time">${getTime()}</span>
                </div>
                <div class="rm-faq-pills">
                    <button class="rm-faq-pill" data-query="Shipping Info">🚚 Shipping</button>
                    <button class="rm-faq-pill" data-query="How to track order">📦 Order Status</button>
                    <button class="rm-faq-pill" data-query="Return policy">🔄 Returns</button>
                    <button class="rm-faq-pill" data-query="Payment options">💳 Payment</button>
                    <button class="rm-faq-pill" data-query="Customer support">📞 Support</button>
                </div>
            `;
            bindPillListeners();
        });

        // Bind FAQ Pill Listeners
        function bindPillListeners() {
            document.querySelectorAll('.rm-faq-pill').forEach(function (pill) {
                pill.addEventListener('click', function () {
                    const query = this.getAttribute('data-query');
                    sendMessage(query);
                });
            });
        }
        bindPillListeners();

        // Send Handlers
        chatSend.addEventListener('click', function () {
            sendMessage(chatInput.value);
        });

        chatInput.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                sendMessage(chatInput.value);
            }
        });

        function sendMessage(text) {
            if (!text || !text.trim()) return;

            const userText = text.trim();
            appendMessage(userText, 'user');
            chatInput.value = '';

            const loadingMsg = appendTypingIndicator();

            fetch(contextPath + '/api/chat', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'message=' + encodeURIComponent(userText)
            })
            .then(function (res) { return res.json(); })
            .then(function (data) {
                loadingMsg.remove();
                if (data.success) {
                    appendMessage(data.reply, 'bot');
                } else {
                    appendMessage('⚠️ ' + (data.error || 'Failed to fetch response.'), 'bot');
                }
            })
            .catch(function () {
                loadingMsg.remove();
                appendMessage('⚠️ Connectivity error. Please try again later.', 'bot');
            });
        }

        function appendMessage(msg, sender) {
            const div = document.createElement('div');
            div.className = 'rm-msg rm-msg-' + sender;
            
            const content = document.createElement('div');
            content.className = 'rm-msg-content';
            content.innerHTML = formatMarkdown(msg);

            const time = document.createElement('span');
            time.className = 'rm-msg-time';
            time.innerText = getTime();

            div.appendChild(content);
            div.appendChild(time);

            chatMessages.appendChild(div);
            chatMessages.scrollTop = chatMessages.scrollHeight;
            return div;
        }

        function appendTypingIndicator() {
            const div = document.createElement('div');
            div.className = 'rm-msg rm-msg-bot';
            div.innerHTML = `
                <div class="rm-typing-dots">
                    <div class="rm-typing-dot"></div>
                    <div class="rm-typing-dot"></div>
                    <div class="rm-typing-dot"></div>
                </div>
            `;
            chatMessages.appendChild(div);
            chatMessages.scrollTop = chatMessages.scrollHeight;
            return div;
        }

        function formatMarkdown(text) {
            if (!text) return '';
            let html = text
                .replace(/&/g, '&amp;')
                .replace(/</g, '&lt;')
                .replace(/>/g, '&gt;')
                .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
                .replace(/\*(.*?)\*/g, '<em>$1</em>')
                .replace(/`(.*?)`/g, '<code>$1</code>')
                .replace(/\n/g, '<br/>');
            return html;
        }

        function getTime() {
            const date = new Date();
            let hours = date.getHours();
            let minutes = date.getMinutes();
            const ampm = hours >= 12 ? 'PM' : 'AM';
            hours = hours % 12;
            hours = hours ? hours : 12;
            minutes = minutes < 10 ? '0' + minutes : minutes;
            return hours + ':' + minutes + ' ' + ampm;
        }
    });
})();
