import ru.itis.game.levels.Game;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.application.ApplicationStartupSettings;
import ru.itis.gengine.base.GSize;

public class Main {
    public static void main(String[] args) {
        Application.shared.run(
                ApplicationStartupSettings.builder()
                        .name("Ultra Racing")
                        .windowTitle("Tatar edition")
                        .startupLevel(new Game())
                        .windowSize(new GSize(800, 1000))
                        .isFullScreen(false)
                        .isServer(args.length > 0 && "start_server".equals(args[0]))
                        .build()
        );
    }
}
