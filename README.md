About
=====
Slick is a blogging engine built on top of Sling and Sightly. It uses Sling Models heavily. The idea was to create a lightweight blogging engine to power [experiencemanaged.com](http://experiencemanaged.com/) using technologies common to AEM.

Demo
====
[dev.experiencemanaged.com](http://dev.experiencemanaged.com/)

Features
========
* Creating and editing posts, pages, media (really any taxonomy is fairly easy to build)
* RSS 2.0 Feed
* Authentication

Planned
=======
* Better support of media
* Better docs
* More editing features
* Pagination
* Better theming support

Installation
============

1. Start Sling
2. Install i18n
3. Install javax.mail
4. Install jcr.compiler
5. Install Sling XSS
6. Install Sightly
7. Deploy Slick 
 * mvn clean install -PautoInstallBundle
 * mvn clean install -PautoInstallBundle -Dsling.host=YOURHOST -Dsling.password=YOURPASSWORD

Configuration
=============

1. Copy the themes folder to /content/slick/
2. Admin is located at http://localhost:8080/auth.html (admin:admin)

Styling
=======

1. Use [Brackets](http://brackets.io) + Brackets SASS Extension
2. Slick theme located in /src/main/resources/themes/slick