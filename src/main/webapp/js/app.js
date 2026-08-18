document.addEventListener('DOMContentLoaded', () => {
    console.log('RaniyaMart App initialized.');

    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            alert.style.transition = 'opacity 0.5s ease';
            setTimeout(() => alert.remove(), 500);
        }, 5000);
    });

    // Floating Chatbot UI Widget Stub
    initChatbotWidget();
});

function initChatbotWidget() {
    const chatbotHtml = `
        <div id="chatbot-widget" style="position: fixed; bottom: 20px; right: 20px; z-index: 9999;">
            <button id="chatbot-toggle" class="btn btn-primary" style="border-radius: 50%; width: 56px; height: 56px; font-size: 1.5rem; box-shadow: 0 8px 24px rgba(99,102,241,0.5);">💬</button>
            <div id="chatbot-panel" style="display: none; position: absolute; bottom: 70px; right: 0; width: 340px; height: 420px; background: #1e293b; border: 1px solid #334155; border-radius: 16px; box-shadow: 0 12px 32px rgba(0,0,0,0.6); flex-direction: column; overflow: hidden;">
                <div style="background: #6366f1; color: white; padding: 12px 16px; font-weight: 700; display: flex; justify-content: space-between; align-items: center;">
                    <span>🤖 RaniyaMart AI Assistant</span>
                    <button id="chatbot-close" style="background: none; border: none; color: white; cursor: pointer; font-size: 1.2rem;">&times;</button>
                </div>
                <div id="chatbot-messages" style="flex: 1; padding: 12px; overflow-y: auto; display: flex; flex-direction: column; gap: 8px; font-size: 0.9rem;">
                    <div style="background: #334155; padding: 8px 12px; border-radius: 10px; align-self: flex-start; max-width: 80%;">
                        Hello! Welcome to RaniyaMart. How can I help you find products today?
                    </div>
                </div>
                <div style="padding: 8px; border-top: 1px solid #334155; display: flex; gap: 6px;">
                    <input type="text" id="chatbot-input" placeholder="Ask a question..." style="flex: 1; padding: 8px 12px; border-radius: 8px; border: 1px solid #334155; background: #0f172a; color: white; font-size: 0.85rem;" />
                    <button id="chatbot-send" class="btn btn-primary" style="padding: 8px 12px; font-size: 0.85rem;">Send</button>
                </div>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML('beforeend', chatbotHtml);

    const toggleBtn = document.getElementById('chatbot-toggle');
    const closeBtn = document.getElementById('chatbot-close');
    const panel = document.getElementById('chatbot-panel');
    const sendBtn = document.getElementById('chatbot-send');
    const input = document.getElementById('chatbot-input');
    const messages = document.getElementById('chatbot-messages');

    if (toggleBtn && panel) {
        toggleBtn.addEventListener('click', () => {
            panel.style.display = panel.style.display === 'none' ? 'flex' : 'none';
        });
        closeBtn.addEventListener('click', () => {
            panel.style.display = 'none';
        });

        const sendMessage = () => {
            const text = input.value.trim();
            if (!text) return;

            // Render User Message
            const userMsg = document.createElement('div');
            userMsg.style.cssText = 'background: #6366f1; color: white; padding: 8px 12px; border-radius: 10px; align-self: flex-end; max-width: 80%;';
            userMsg.textContent = text;
            messages.appendChild(userMsg);
            input.value = '';
            messages.scrollTop = messages.scrollHeight;

            // Render Bot Response (Mock AI Reply)
            setTimeout(() => {
                const botMsg = document.createElement('div');
                botMsg.style.cssText = 'background: #334155; color: white; padding: 8px 12px; border-radius: 10px; align-self: flex-start; max-width: 80%;';
                
                if (text.toLowerCase().includes('shipping') || text.toLowerCase().includes('delivery')) {
                    botMsg.textContent = 'Standard shipping takes 2-4 business days across all orders!';
                } else if (text.toLowerCase().includes('return') || text.toLowerCase().includes('refund')) {
                    botMsg.textContent = 'We offer a 30-day hassle-free return policy on all products.';
                } else {
                    botMsg.textContent = 'I am your RaniyaMart assistant. You can browse electronics, fashion, and home goods in our catalog!';
                }

                messages.appendChild(botMsg);
                messages.scrollTop = messages.scrollHeight;
            }, 600);
        };

        sendBtn.addEventListener('click', sendMessage);
        input.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') sendMessage();
        });
    }
}
