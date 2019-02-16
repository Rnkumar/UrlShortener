## UrlShortener

### Manual For Working

	1. Run the build.gradle file to download the Dependencies.
	2. Build the project
	3. Run mongodb in localhost first or change the application.properties file to your mongodb host and port.
	4. Run the project (The website will be hosted in http://localhost:8443 (Do not change the port number,if changed ,also change the ones in the source code.))
	5. index.html - HomePage;  urllist.html - List of Urls Page;

### Dependencies
	
    implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-rest'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation(group: 'org.springframework.security', name: 'spring-security-test')
    testImplementation(group: 'junit', name: 'junit')

### File Locations:

		src/main/java  -  Java Files
		src/main/resource - Html Files(index.html,urllist.html)
		(Note: angular cdn url is used - Need internet)
		
### Screenshots:

	![Home Page](https://github.com/Rnkumar/UrlShortener/blob/master/screenshots/homepage.png "Home Page")
	
	![UrlList Page](https://github.com/Rnkumar/UrlShortener/blob/master/screenshots/urllistpage.png "UrlList Page")

### Video Demo 

	[Youtube Link Click Here](https://youtu.be/wsbUtzCN_jQ)
	
