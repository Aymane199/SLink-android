# SLink : Save & Share Links
**SLink** is an app that allows you to create and share your own library of links.

#### Feature provided :
- Connect using Gmail.
- Create folders where you can put the links
- Save links by either copy/paste the link in desired folder or share the link to the SLink app
- Share folder with other users (folder members)
- Like, Comment & save Links
- Get notified whenever a member add a link or a comment
- Links are stored with a preview automatically generated
- Visite links inside the app (if desired) 
- Share links to outside the app

# User Interface
[Full prototype with figma](https://www.figma.com/file/hHpYJ3TWeo1PSPcAVX0EEu/SLink?node-id=0%3A1 "Full prototype with figma")

Overview

 [![](https://slink.s3.us-east-2.amazonaws.com/overview1.png)](https://slink.s3.us-east-2.amazonaws.com/overview1.png)
[![](https://slink.s3.us-east-2.amazonaws.com/overview3.png)](https://slink.s3.us-east-2.amazonaws.com/overview3.png)

# Architecture overview
The app is a stateless client: all operations are performed
by calling api endpoints over the network.

Local data is in effect immutable, the client just downloads versions of data as needed. 

The data is stored as Observables objects in Model class. We interact with the api using REST client retrofit which is implemented in repository classes, repository classes send requests and according to the response it updates the model objects.  

- /**Activities**	*(contains all activities of the app)*
- /**Adapter**	*(contains dataAdapters)*
- /**Component**	*(contains reusable components)*
- /**Fragment** 	*(contains all fragments of the app)*
- /**Listener**	*(contains listener)*
- /**Model**		*(contains observable objects)*
- /**Repository**	*(contains implementation of retrofit interface - send requests to Api & update model’s objects)*
- /**Retrofit**	*(contains retrofit interfaces)*
- /**Services**	*(contains background services)*
- /**Table**		*(contains entities used)*
- /**utils**		*(contains enum classes & useful classes)*

# External dependencies
- [Retrofit](https://square.github.io/retrofit/ "Retrofit") : Consume api service
- [Picasso](https://github.com/square/picasso "Picasso") : Display images 
- [Richlinkpreview](https://github.com/ponnamkarthik/RichLinkPreview "Richlinkpreview") : Get link preview
- [Play-services-auth ](https://developers.google.com/identity/sign-in/android/start "Play-services-auth ") : Sign in with google account
- [Firebase-messaging ](https://firebase.google.com/docs/cloud-messaging "Firebase-messaging ") : Real time notification (http push)

# License

`Slink android is distributed under the GPL-3.0 License.  
Copyright (c) 2020 Rizke Aymane`


