# Discord Guild Management Admin

I enjoy playing MMOs and one of the primary activities is guild organized events such as raids. These events require coordination and tracking 
of all of the members involved in the raid. One of the currently most popular communication tools is [Discord](https://discordapp.com). This project 
is a personal endeavor in buildig an admin API service to power a Discord Bot and an SPA admin tool to manage and track raid events.

Currently, as of version 1.0.0-SNAPSHOT, the UI is generated on the server side. This is temporary in order to 
get the larger moving pieces in place. In an upcoming version I will be splitting the UI to an SPA framework and 
this project will just provide a RESTful API. 

## Create a Discord App And Generate Client Secrets

1. Visit [https://discordapp.com/developers/applications/me]
2. Click on `New App`.
3. Give your application a name.
4. Add a redirect URL. E.g. if you're running this on your laptop/desktop ensure the URL is one that resolves in your browser to your own computer. By default what the Spring boot application expects is: http://<host that you can be redirected to>:8080/login/oauth2/code/discord
5. Make a note of the `Client ID` under `APP DETAILS`. You will need it in the step below.
6. Click `click to reveal` next to `Client Secret` and make a note of this value. You will need it in the step below. 

## Installing

*Note: as of this current version, 1.0.0-SNAPSHOT, the project hasn't been really made prod ready.*

1. Clone this project to a location of your choosing.
2. Run `mvn clean prepare-package war:exploded` in the location where you cloned the project. E.g. `/foo/bar/projects/discord-guild-management-admin`
3. Optional: Create and export an environment variable called `DISCORD_CLIENT_ID` and assign the value from step 5 above.
4. Optional: Create and export an environment variable called `DISCORD_CLIENT_SECRET` and assign the value from step 6 above.

## Running

If you created the environment variables in steps 3 and 4 above then:

```bash
$ mvn spring-boot:run
```

If you didn't specify the environment variables then you need to pass them into maven

```bash
$ mvn -DDISCORD_CLIENT_ID=<value from step 5 in create app> -DDISCORD_CLIENT_SECRET=<value from step 6 in create app> spring-boot:run
```

## Authors

* **Allan Ditzel** - *Initial work* - [aditzel](https://github.com/aditzel)

## License

This project is licensed under the ISC License - see the [LICENSE.md](LICENSE.md) file for details.

