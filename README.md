## GiphyExplorer

### Specs
Full Kotlin, Full RxJava3, No database (online only), Full Dagger2, Custom "Shaper" arch, MotionLayout, Glide + Lottie, a few integration test

### Architecture
This project uses only 3 layers of architecture, UI, "Shaper" and Endpoint. Domain classes are used transitively across every layer.
Endpoint are used only make CRUD op for every object from the Domain, they make request and they parse the result.
UI is the UI, there is nothing special to say here, it contains `Fragment`, `Activity`, etc.

"Shaper" is the place where the business logic is written, they are written this way:
* The UI request a ressource to the shaper
* The shaper ask the endpoint for data
* Apply his own business logic to the returned data
* Returns the data updated by the business logic

### Meaning of "Shaper"
A Shaper is a shape for a domain object, it get domain objects in his input and returns a transformed domain object.
The process of transformation is actually the business logic of the app !

Because the dataflow of this project is unirectionnal, you cannot send data to a Shaper, a Shaper request for data for you, transform it and returns to the UI
as long you are subscribed to the Shaper. To "send" data to a Shaper, you must initialize the Shaper with the value, in my case, I use IOC with Dagger2 to do this job.

By using this approach, the dataflow between the UI and a shaper is circular and unidirectionnal in this way UI -> SubComponent -> Shaper -> UI -> etc...

Feel free to ask me questions by creating an issue on github.
