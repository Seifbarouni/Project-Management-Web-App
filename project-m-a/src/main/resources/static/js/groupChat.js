const groupChat = document.getElementById("group-chat")
const messagesContainer = document.getElementById("messages-container")
const messageForm = document.getElementById("message-form")
const projectId = document.getElementsByTagName("main")[0].id
const username = document.getElementsByTagName("main")[0].className
let stompClient

function connect() {
    const socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.debug = null

    stompClient.connect({}, onConnected, null);
}
connect()

function onConnected() {
    stompClient.subscribe(`/topic/${projectId}`, onMessageReceived);

    stompClient.send(`/app/chat.newUser/${projectId}`,
        {},
        JSON.stringify({ sender: username, type: "JOIN" })
    )
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    if (message.type === "JOIN") {
        const newMessage = document.createElement("div")
        newMessage.classList = "border-b-2 border-gray-300 text-sm flex"
        newMessage.innerHTML = `<div class="flex-1 ml-1">
 <span class="font-bold text-lg">🤖 PMA Bot</span>  
 <div class="pb-1 ml-1"><span class="font-bold">${message.sender} </span>has joined the chat! 😎</div>
</div>`
        messagesContainer.appendChild(newMessage)

    } else if (message.type === 'LEAVE') {
        const newMessage = document.createElement("div")
        newMessage.classList = "border-b-2 border-gray-300 text-sm flex"
        newMessage.innerHTML = `<div class="flex-1 ml-1">
 <span class="font-bold text-lg">🤖 PMA Bot</span>  
 <div class="pb-1 ml-1"><span class="font-bold">${message.sender}</span> has left the chat 👋</div>
</div>`
        messagesContainer.appendChild(newMessage)
    } else {
        let newMessage = document.createElement("div")
        newMessage.classList = "border-b-2 border-gray-300 text-sm flex"
        newMessage.innerHTML = `<span class="flex-none p-1"><img  class=" h-6 w-6 rounded-full ring-2 ring-white" src="https://ui-avatars.com/api/?background=random&name=${message.sender}" 
        alt="avatar"></span>
    <div class="flex-1 ml-1">
     <span class="font-bold text-lg">${message.sender} </span><span class="text-xs text-gray-600">${message.time}</span>   
     <div class="pb-1 ml-1">${message.content}</div>
    </div>`
        messagesContainer.appendChild(newMessage)
        if (message.sender === username) {
            let payload = {
                sender: message.sender,
                content: message.content,
                time: message.time,
                projectId: projectId
            }
            async function AddMessage() {
                let res = await fetch("http://localhost:9003/messages/addMessage", {
                    method: "POST",
                    headers: {
                        'Content-type': 'application/json',
                    },
                    body: JSON.stringify(payload),
                })
                if (res.ok) {
                    let txt = await res.text()
                    return txt
                } else {
                    return `HTTP error: ${res.status}`
                }
            }
            AddMessage().then(data => {
                if (data != "Success" && data != "") {
                    alert(data)
                }
            })
        }

    }
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

function openGroupChat() {
    groupChat.style.visibility = "visible"
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

function closeGroupChat() {
    groupChat.style.visibility = "hidden"
}


messageForm.addEventListener("submit", e => {
    e.preventDefault()
    if (messageForm.message.value != "") {
        const current_datetime = new Date()
        let formatted_date = current_datetime.getFullYear() + "/" + (current_datetime.getMonth() + 1) + "/" + current_datetime.getDate() + " " + current_datetime.getHours() + ":" + current_datetime.getMinutes() + ":" + current_datetime.getSeconds()
        const Message = {
            sender: username,
            content: messageForm.message.value,
            type: "CHAT",
            time: formatted_date,
            projectId: projectId
        }
        stompClient.send(`/app/chat.send/${projectId}`, {}, JSON.stringify(Message))
        messageForm.message.value = ""
    }
})

window.onbeforeunload = () => {
    const Message = {
        sender: username,
        content: "",
        type: "LEAVE",
        time: "",
        projectId: projectId
    }
    stompClient.send(`/app/chat.send/${projectId}`, {}, JSON.stringify(Message))
    stompClient.disconnect()
}