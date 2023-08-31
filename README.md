The “cloud file storage” project
Multi-user file cloud. Users of the service can use it to download and store files. The source of inspiration for the project is Google Drive.

Using Spring Boot features
Working with Docker and Docker Compose
Database structure development
NoSQL storage usage - S3 for files, Redis for sessions

Application functionality
Working with users:

Registration
Authorization
Logout
Working with files and folders:

Uploading files and folders
Creating a new empty folder (similar to creating a new folder in Explorer)
Removal
Renaming

Application Interface
Main page
The address is /?path=$path_to_subdirectory. The $path parameter specifies the path of the folder being viewed. If the parameter is omitted, the root folder is assumed. Example - /path=Projects%2FJava%2FCloudFileStorage (the parameter is encoded via URL Encode).

Heading
For unauthorized users - registration and authorization buttons
For authorized users - the login of the current user and the Logout button
Content (only for authorized users)
Search form for files and folders by name
Navigation chain (breadcrumbs) containing the path from folders to the current folder. Each item is a link to its own folder. Example - a chain of folders leading to - Projects/Java/CloudFileStorage would contain 3 folders - root, Projects and Projects/Java
A list of files in the current directory. For each file, we display a name and a button that calls up the action menu (delete, rename)
Forms (or drop areas) for uploading files and folders.

File Search Page
Address - /search/?query=$search_query.

Heading
For unauthorized users - registration and authorization buttons
For authorized users - the login of the current user and the Logout button
Content
Search form for files and folders by name
List of found files. For each file found, we display the name and a button to go to the folder containing this file.

Spring Security is responsible for authorization and access control to pages.

Spring Sessions is responsible for working with sessions. By default, Spring Boot stores sessions inside the application, and they are lost after each restart of the application. We will use Redis to store sessions.

Redis is a NoSQL storage that has a built-in TTL (time to live) attribute for records, which makes it convenient for storing sessions - expired sessions are automatically deleted.

S3 File Storage
We will use S3 - simple storage service to store files. The project developed by Amazon Cloud Services is a cloud service and a protocol for file storage. In order not to depend on Amazon's paid services in this project, we will use an alternative S3-compatible storage that can be run locally - https://min.io/

Docker image for local launch of MinIO - https://hub.docker.com/r/minio/minio/
To work with the S3 protocol, use the Minio Java SDK


S3 Storage Structure
In SQL we operate with tables, in S3 there are no tables, instead S3 operates with buckets (bucket - basket) with files. To understand what a bucket is, you can draw an analogy with a disk or a USB flash drive.

You can create files and folders inside the bucket.

To store the files of all users in the project, we will create a bucket for them called user-files. In the root of the bucket, a folder named in the format user-${id}-files will be created for each user, where id is the user ID from the SQL database.

Each of these folders is the root for storing the folders of this user. Example file docs/test.txt the user with id 1 must be saved to the path user-1-files/docs/test.txt .

Working with S3 from Java
As mentioned above, we will use the AWS Java SDK to work with S3.

Upload files
To upload files, you need to use HTML file input - https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/file . A common approach is to arrange this in the form of a zone to which you can drag files from explorer, for example - https://codepen.io/dcode-software/pen/xxwpLQo .

At the HTTP level, file transfer is carried out using multipart/form-data.

On the Spring Boot side, it will be necessary to implement the controller(s) to process the downloaded files. It is important to keep in mind that by default the limit for uploading files to Spring Boot is 10 megabytes, but it can be increased.

Tests
Instead of using H2, I suggest using Testcontainers to run tests in the context of a full-fledged (and not in-memory) database. This allows you to bring the test environment closer to the working environment, and test nuances specific to specific database engines.


Docker
In this project, for the first time, we will use Docker to conveniently launch the necessary applications - SQL database, MinIO file storage and Redis session storage.
