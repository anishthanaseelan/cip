# CIP Client

***

## Quick Start

### Prerequisites
1. NodeJS
2. GIT
3. GIT Extensions

### Downloading and Running the CIP Client
1. Download the source code.

2. Once source is downloaded use npm (node package manager) to download all the required
   development tools & build task dependencies.
	```sh
	command: $npm install

3. Now use bower to download all the project related dependencies.
   Note: $npm install also executes $bower install, by default. so do this step explicitly only when you face issues with bower_components.
	```sh
	command: $bower install

4. Modify the service URL's.
Make sure you modify the constants for service url's present in /cip-client/appConfig.js file to point to the right server.

5. Now use gulp to build the client. This step would generate a directory under the name '/build' and copy all the
   html and js files over to the directory.
	```sh
	command: $gulp build

6. Finally, launch the application by opening the '/build/index.html' file on a Chrome browser.
	Note: The application has to run on a working web server for it to be successfully launched. Both Visual Studio &
	Webstorm provide an in-built web server to run the application. If you use any other IDE, then you must launch the
	web server first before launching the application.

###Troubleshooting
1. Facing any issues while running $npm install or $bower install commands? Try cleaning up the cache by running the following commands
   and try running the commands again.
	```sh
	$npm cache clean
	$bower cache clean

	$npm install
	$bower install

2. Facing proxy related issues?
   Set up proxy by running the below commands.
	```sh
	$npm config set strict-ssl false
	$npm config set registry "http://registry.npmjs.org/"
	$npm config set proxy http://proxy_host:port
	$npm config set https-proxy http://proxy_host:port

