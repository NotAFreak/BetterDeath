package net.notafreak.betterdeath.config;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.notafreak.betterdeath.BetterDeath;
import org.joml.Vector2i;
import java.util.Locale;

public class ClientConfig {
	public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
	public static ModConfigSpec SPEC = null;

    // Color config
	public static ModConfigSpec.ConfigValue<Integer> deathScreenR;
	public static ModConfigSpec.ConfigValue<Integer> deathScreenG;
	public static ModConfigSpec.ConfigValue<Integer> deathScreenB;

    // Texture config
    public static ModConfigSpec.ConfigValue<Boolean> deathScreenTextureActive;
    public static ModConfigSpec.ConfigValue<Boolean> deathScreenTextureIsFilepath;
    public static ModConfigSpec.ConfigValue<String> deathScreenTexture;
    public static ModConfigSpec.ConfigValue<String> deathScreenTexturePosition;
    public static ModConfigSpec.ConfigValue<String> deathScreenTextureSize;

    public static ModConfigSpec.ConfigValue<Double> deathScreenTextureFadeInTime;
    public static ModConfigSpec.ConfigValue<Double> deathScreenTextureFadeOutTime;
    public  static  Vector2i GetPosition() {
        Vector2i pos = new Vector2i();
        String sanitized = deathScreenTexturePosition.get().toLowerCase(Locale.ROOT).replace(" ", "");
        String[] tokens = sanitized.split(",");
        pos.x = Integer.parseInt(tokens[0]);
        pos.y = Integer.parseInt(tokens[1]);
        return  pos;
    }
    public  static  Vector2i GetSize() {
        Vector2i size = new Vector2i();
        String sanitized = deathScreenTextureSize.get().toLowerCase(Locale.ROOT).replace(" ", "");
        String[] tokens = sanitized.split(",");
        size.x = Integer.parseInt(tokens[0]);
        size.y = Integer.parseInt(tokens[1]);
        return  size;
    }

	static {
		BUILDER.push("Color Settings");

		deathScreenR = BUILDER.comment("Red value of the death overlay").defineInRange("Death screen R", 0, 0, 255);
		deathScreenG = BUILDER.comment("Green value of the death overlay").defineInRange("Death screen G", 0, 0, 255);
		deathScreenB = BUILDER.comment("Blue value of the death overlay").defineInRange("Death screen B", 0, 0, 255);
		BUILDER.pop();

        /*
        BUILDER.push("Texture Settings");
        deathScreenTextureActive = BUILDER.comment("Whether or not to display a texture on the death screen").define("Death Screen Texture Active", false);
        deathScreenTextureIsFilepath = BUILDER.comment("Whether or not the texture is an actual file path on your system").define("Death Screen Texture Is File Path", false);
        deathScreenTexture = BUILDER.comment("Texture MRL to display - Either be a minecraft texture (eg: minecraft:block/stone) or filepath relative to .minecraft/config").define("Death Screen Texture", "minecraft:block/stone");

        deathScreenTexturePosition = BUILDER.comment("Position offset from the top left of the screen for the top left of the texture").define("Death Screen Texture Position", "0,0");
        deathScreenTextureSize = BUILDER.comment("Size in screen pixels for the death screen texture to display to").define("Death Screen Texture Size", "1024,1024");

        deathScreenTextureFadeInTime = BUILDER.comment("Time for the death screen to fade in for").define("Death Screen texture fade in time", 0.0);
        deathScreenTextureFadeOutTime = BUILDER.comment("Time for the death screen to fade out for").define("Death Screen texture fade out time", 0.5);
        BUILDER.pop();
        */
		SPEC = BUILDER.build();
	}
}
