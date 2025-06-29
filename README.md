# Music Agent

A Java application for music management and processing. This project provides a robust foundation for building music-related applications with features like song management, search capabilities, and extensible architecture.

## Features

- **Song Management**: Add, update, delete, and retrieve songs
- **Search Functionality**: Search songs by title, artist, or album
- **In-Memory Storage**: Fast, concurrent access to song data
- **Logging**: Comprehensive logging with SLF4J and Logback
- **Testing**: JUnit 5 test suite with comprehensive coverage
- **Maven Build**: Standard Maven project structure

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Project Structure

```
music-agent/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/musicagent/
│   │   │       ├── MusicAgentApplication.java    # Main application class
│   │   │       ├── MusicAgent.java               # Core agent class
│   │   │       ├── model/
│   │   │       │   └── Song.java                 # Song data model
│   │   │       └── service/
│   │   │           ├── MusicService.java         # Service interface
│   │   │           └── impl/
│   │   │               └── MusicServiceImpl.java # Service implementation
│   │   └── resources/
│   │       └── logback.xml                       # Logging configuration
│   └── test/
│       └── java/
│           └── com/musicagent/
│               └── MusicAgentTest.java           # Test suite
├── pom.xml                                       # Maven configuration
└── README.md                                     # This file
```

## Getting Started

### 1. Clone or Download the Project

Navigate to your desired directory and ensure you have the project files.

### 2. Build the Project

```bash
mvn clean compile
```

### 3. Run Tests

```bash
mvn test
```

### 4. Run the Application

```bash
mvn exec:java -Dexec.mainClass="com.musicagent.MusicAgentApplication"
```

Or build and run the JAR:

```bash
mvn clean package
java -jar target/music-agent-1.0.0.jar
```

## Usage Examples

### Basic Song Management

```java
// Create a music service
MusicService musicService = new MusicServiceImpl();

// Add a song
Song song = new Song();
song.setTitle("Bohemian Rhapsody");
song.setArtist("Queen");
song.setAlbum("A Night at the Opera");
song.setDuration(Duration.ofMinutes(6).plusSeconds(7));
song.setGenre("Rock");
song.setYear(1975);

Song addedSong = musicService.addSong(song);

// Search for songs
List<Song> queenSongs = musicService.searchByArtist("Queen");
List<Song> rhapsodySongs = musicService.searchByTitle("Rhapsody");

// Get song by ID
Optional<Song> retrievedSong = musicService.getSongById(addedSong.getId());
```

### Running Tests

The project includes comprehensive tests that demonstrate the functionality:

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=MusicAgentTest

# Run with detailed output
mvn test -Dtest=MusicAgentTest -Dsurefire.useFile=false
```

## Configuration

### Logging

The application uses Logback for logging. Configuration is in `src/main/resources/logback.xml`:

- Console output with timestamp and log level
- File logging with daily rotation
- Debug level logging for the `com.musicagent` package

### Maven Configuration

The `pom.xml` includes:

- Java 17 compilation target
- JUnit 5 for testing
- SLF4J and Logback for logging
- Jackson for JSON processing
- Maven plugins for compilation, testing, and packaging

## Extending the Project

### Adding New Features

1. **New Service Methods**: Extend the `MusicService` interface and implementation
2. **New Models**: Add new model classes in the `model` package
3. **New Services**: Create new service interfaces and implementations
4. **Persistence**: Replace the in-memory storage with database persistence

### Example: Adding Playlist Support

```java
// Create a Playlist model
public class Playlist {
    private String id;
    private String name;
    private List<Song> songs;
    // ... getters and setters
}

// Extend MusicService
public interface MusicService {
    // ... existing methods
    Playlist createPlaylist(String name);
    void addSongToPlaylist(String playlistId, String songId);
    List<Song> getPlaylistSongs(String playlistId);
}
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Run the test suite
6. Submit a pull request

## License

This project is open source and available under the MIT License.

## Support

For questions or issues, please create an issue in the project repository. 