console.log("\n\n\n\n\n[Script] Custom script loading!");

const WebSocket = require('ws');

var connection = new WebSocket('ws://127.0.0.1:4444');

const sendVoiceCommand = (command) => connection.send(command);

function getGPTCommand() {
	console.log("[getGPTCommand] Triggered!");
	
	return new Promise((resolve) => {
		console.log("[getGPTCommand|Promise] Triggered!");
		
		connection.onmessage = (e) => {
			console.log("[getGPTCommand|Promise|onmessage] Triggered!");
			resolve(e.data);
		}
	});
}

var commandCounter = 0;
serenade.global().command("gpt <%text%>", async (api, matches) => {
	sendVoiceCommand(matches.text);

	let gptCommand = await getGPTCommand();
	console.log(`[${commandCounter}][Command] Recieved GPT resonse: ${gptCommand.replaceAll("\n", "<\\n>").replaceAll("\t", "<\\t>").replaceAll("    ", "<\\s>")}`);
	
	let parts = gptCommand.split(" ");
	
	let type = parts[0];
	console.log(`[${commandCounter}][Command] Type ${type}!`);
	
	let command = parts.slice(1).join(" ");
	console.log(`[${commandCounter}][Command] Command: ${command.replaceAll("\n", "<\\n>").replaceAll("\t", "<\\t>").replaceAll("    ", "<\\s>")}`);
	
	
	if(type == "direct") {
		noMSpace = command.replaceAll("    ", "");
	
		command = noMSpace;
		
		console.log(`[${commandCounter}][Command] Typing command!`);
		await api.typeText(command);
	} else if(type == "keyword") {
		console.log(`[${commandCounter}][Command] Running command!`);
		await api.runCommand(command);
	} else {
		console.log("Fakt");
	}
	
	console.log(`[${commandCounter}][Command] Complete!`);
});