/**
 * RaniyaMart Floating AI Chatbot Widget Component
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
                            <small>Instant Shopping & Order Help</small>
                        </div>
                        <button id="rm-chat-close">&times;</button>
                    </div>
                    <div class="rm-chat-body" id="rm-chat-messages">
                        <div class="rm-msg rm-msg-bot">
                            Hello! 👋 How can I help you with RaniyaMart today? Try the quick questions below or type your inquiry!
                        </div>
                        <div class="rm-faq-pills">
                            <button class="rm-faq-pill" data-query="Shipping Info">🚚 Shipping</button>
                            <button class="rm-faq-pill" data-query="How to track order">📦 Order Status</button>
                            <button class="rm-faq-pill" data-query="Return policy">🔄 Returns</button>
                            <button class="rm-faq-pill" data-query="Payment options">💳 Payment</button>
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
                box-shadow: 0 8px 24px rgba(79, 70, 229, 0.35);
                display: flex;
                align-items: center;
                gap: 8px;
                transition: transform 0.2s ease, box-shadow 0.2s ease;
            }
            #rm-chat-btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 12px 28px rgba(79, 70, 229, 0.45);
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
                width: 360px;
                height: 480px;
                background: #1e1e2e;
                color: #cdd6f4;
                border: 1px solid rgba(255, 255, 255, 0.1);
                border-radius: 16px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4);
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
            #rm-chat-close {
                background: transparent;
                border: none;
                color: #a6adc8;
                font-size: 20px;
                cursor: pointer;
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
                padding: 10px 14px;
                border-radius: 12px;
                font-size: 13px;
                line-height: 1.5;
                white-space: pre-wrap;
            }
            .rm-msg-bot {
                background: #313244;
                color: #cdd6f4;
                align-self: flex-start;
                border-bottom-left-radius: 2px;
            }
            .rm-msg-user {
                background: #6366f1;
                color: #ffffff;
                align-self: flex-end;
                border-bottom-right-radius: 2px;
            }
            .rm-faq-pills {
                display: flex;
                flex-wrap: wrap;
                gap: 6px;
                margin-top: 4px;
            }
            .rm-faq-pill {
                background: rgba(99, 102, 241, 0.15);
                color: #818cf8;
                border: 1px solid rgba(99, 102, 241, 0.3);
                border-radius: 20px;
                padding: 5px 10px;
                font-size: 11px;
                cursor: pointer;
                transition: background 0.2s;
            }
            .rm-faq-pill:hover {
                background: rgba(99, 102, 241, 0.3);
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
                border: 1px solid rgba(255, 255, 255, 0.1);
                border-radius: 8px;
                padding: 8px 12px;
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
                padding: 8px 14px;
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

        // FAQ Pills
        document.querySelectorAll('.rm-faq-pill').forEach(function (pill) {
            pill.addEventListener('click', function () {
                const query = this.getAttribute('data-query');
                sendMessage(query);
            });
        });

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

            const loadingMsg = appendMessage('Typing...', 'bot');

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
            div.innerText = msg;
            chatMessages.appendChild(div);
            chatMessages.scrollTop = chatMessages.scrollHeight;
            return div;
        }
    });
})();
