
oauth2.0 &amp; youtube google api and scheduled job used to store data
# youtubedata
1. This application is to store you tube video data
2. mongo db is used to store data
3. google outh2.0 used to authorize google youtube api (doc reference: https://developers.google.com/youtube/v3/getting-started)
4. for Authorization you have to create your client secret json put in root directory
5. and update Constant.java CLIENT_SECRETS variable , 
6. Mention SCOPES,APPLICATION_NAME, AUTHORIZATION_USER in Constant.java
7. update MONGO_URI to path mongodb in my case it is mongodb://localhost:27017
8. it will create db with name youtubedata and store doc in collection video
9. For callback authorisation need to add callback_uri in google console's Authorised redirect URIs

you can change search-criteria in application.yml  query and publishedafter
bydefault it will be as below:
search-criteria:
query: 'spring boot'
publishedafter: '2023-01-01T00:00:00Z'

to run this application take clone of this repo in your local system perform above mentioned step and run this maven project
the scheduler run for every 10 seconds and get youtube search data with next page token and store in mongodb

To fetch and search store data follow youtubedataservice microservice
https://github.com/AnkitSaurabh1795/youtubedataservice
