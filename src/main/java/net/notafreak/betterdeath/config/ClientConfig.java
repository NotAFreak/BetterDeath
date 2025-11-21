package net.notafreak.betterdeath.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.joml.Vector2i;

import java.util.Locale;

public class ClientConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec SPEC = null;

    // Color config
	public static ForgeConfigSpec.ConfigValue<Integer> deathScreenR;
	public static ForgeConfigSpec.ConfigValue<Integer> deathScreenG;
	public static ForgeConfigSpec.ConfigValue<Integer> deathScreenB;

    // Texture config
    public static ForgeConfigSpec.ConfigValue<Boolean> deathScreenTextureActive;
    public static ForgeConfigSpec.ConfigValue<Boolean> deathScreenTextureIsFilepath;
    public static ForgeConfigSpec.ConfigValue<String> deathScreenTexture;
    public static ForgeConfigSpec.ConfigValue<String> deathScreenTexturePosition;
    public static ForgeConfigSpec.ConfigValue<String> deathScreenTextureSize;
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
    public static ForgeConfigSpec.ConfigValue<Double> deathScreenTextureFadeInTime;
    public static ForgeConfigSpec.ConfigValue<Double> deathScreenTextureFadeOutTime;

	static {
		BUILDER.push("Color Settings");

		deathScreenR = BUILDER.comment("Red value of the death overlay").defineInRange("Death screen R", 0, 0, 255);
		deathScreenG = BUILDER.comment("Green value of the death overlay").defineInRange("Death screen G", 0, 0, 255);
		deathScreenB = BUILDER.comment("Blue value of the death overlay").defineInRange("Death screen B", 0, 0, 255);
		BUILDER.pop();

        BUILDER.push("Texture Settings");
        deathScreenTextureActive = BUILDER.comment("Whether or not to display a texture on the death screen").define("Death Screen Texture Active", false);
        deathScreenTextureIsFilepath = BUILDER.comment("Whether or not the texture is an actual file path on your system").define("Death Screen Texture Is File Path", false);
        deathScreenTexture = BUILDER.comment("Texture MRL to display - Either be a minecraft texture (eg: minecraft:block/stone) or filepath relative to .minecraft/config").define("Death Screen Texture", "minecraft:block/stone");

        deathScreenTexturePosition = BUILDER.comment("Position offset from the top left of the screen for the top left of the texture").define("Death Screen Texture Position", "0,0");
        deathScreenTextureSize = BUILDER.comment("Size in screen pixels for the death screen texture to display to").define("Death Screen Texture Size", "1024,1024");

        deathScreenTextureFadeInTime = BUILDER.comment("Time for the death screen to fade in for").define("Death Screen texture fade in time", 0.0);
        deathScreenTextureFadeOutTime = BUILDER.comment("Time for the death screen to fade out for").define("Death Screen texture fade out time", 0.5);
        BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
