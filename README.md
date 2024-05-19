# ChatGPT-voice-code-generator

## Project description
The idea behind this project was to create a voice coding tool which makes use of ChatGPT and
the Serenade tool for a more syntax-free voice command approach. The tool utilizes Serenades
voice recognition and API to access user voice commands when the command is started with the specific
keyword `"gpt"`. The ChatGPT-voice-code-generator accesses the voice command through a local WebSocket
server and sends the command to ChatGPT through their API. The ChatGPT response is sent back to the
Serenade application and the command is executed.

The ChatGPT-voice-code-generator tool has four options:
1. ChatGPT version 4.0 with direct code generation, which means that ChatGPT will try to respond with code
   to be generated with the Serenade "insert" keyword prepended.
2. ChatGPT version 4.0 with keyword code generation, which means that ChatGPT will try to respond with
   keywords of the voice command related to the Serenade syntax.
3. ChatGPT version 3.5 with direct code generation, which means that ChatGPT will try to respond with code
   to be generated with the Serenade "insert" keyword prepended.
4. ChatGPT version 3.5 with keyword code generation, which means that ChatGPT will try to respond with
   keywords of the voice command related to the Serenade syntax.

## Execution instructions
You will need the Serenade AI application, which can be downloaded from here: https://serenade.ai/. When Serenade
is installed, the `custom.js` file, which contains the Serenade API script, needs to be placed in the
`${user.home}/.serenade/scripts` folder to use the tool.

To run the tool, the jar file must first be built. For this, maven has been configured to do this automatically when 
the `mvn clean verify` command is run in the base directory of the project. This should produce an executable jar file 
in the `./target` directory which can then be executed. There will be two generated jar files in target. The one whose 
name ends with `jar-with-dependencies.jar` is built for portable use, and should therefore be used with these 
instructions. However, depending on the underlying environment, there seems to be a lot of inconsistencies with how 
the file wants to be executed. However, with all tested environments, executions from the terminal always work:

1. Make sure the Serenade AI application is installed.
2. Download the GUI repository.
3. Move/replace `custom.js` file to `${user.home}/.serenade/scripts` folder.
4. Add a ChatGPT token in the environment file `.env` in the `./resources` folder.
5. Run the command mvn clean verify in the base directory.
6. Navigate to the `.jar` file in the `/target` folder.
7. Execute the `.jar` file using the command `java -jar "path/to/filename.jar"`
8. Run the Serenade application and use the keyword "gpt" before each voice command to activate the API script.
