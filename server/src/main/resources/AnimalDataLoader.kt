import java.io.File

// This function reads the animals.json file from the server/resources/data folder
fun loadAnimalData(): String {
    // Adjust the path if necessary, based on where your project is located
    val path = "src/main/resources/data/animals.json"
    return File(path).readText()  // Reads the JSON file content and returns it as a string
}
