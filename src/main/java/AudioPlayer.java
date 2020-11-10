public class AudioPlayer extends Product implements MultimediaControl{

    String supportedAudioFormats;
    String supportedPlaylistFormats;

    public AudioPlayer() {
    }

    public AudioPlayer(String name, String manufacturer, String supportedAudioFormats, String supportedPlaylistFormats) {
        super(name, ItemType.AUDIO, manufacturer);
        this.supportedAudioFormats = supportedAudioFormats;
        this.supportedPlaylistFormats = supportedPlaylistFormats;
    }

    @Override
    public void play() { System.out.println("Playing"); }

    @Override
    public void stop() { System.out.println("Stopping"); }

    @Override
    public void previous() { System.out.println("Previous"); }

    @Override
    public void next() { System.out.println("Next"); }

    public String toString(){
        return "Name: " + name + "\n"
                + "Manufacturer: " + manufacturer + "\n"
                + "Type: " + type + "\n"
                + "Supported Audio Formats: " + supportedAudioFormats + "\n"
                + "Supported Playlist Formats: " + supportedPlaylistFormats;
    }

}

