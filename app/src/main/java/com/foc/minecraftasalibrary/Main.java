/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.foc.minecraftasalibrary;

import java.io.IOException;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static final String MCVersion = "1.19.2";
    public static final String MappingsURL = "https://maven.quiltmc.org/repository/release/org/quiltmc/quilt-mappings/1.19.2+build.14/quilt-mappings-1.19.2+build.14-tiny.gz";
    public static final Path MappingsFile = Path.of(MCVersion+".mappings.tiny.gz");
    public static final Path officialJar = Path.of("minecraft."+MCVersion+".official.jar");
    public static final Path unpackedJar = Path.of("minecraft."+MCVersion+".unpacked.jar");
    public static final Path remappedJar = Path.of("minecraft."+MCVersion+".remapped.jar");

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Checking if remapped jar exists...");

        if (!Files.exists(remappedJar)) {
            System.out.println("Downloading official Minecraft jar...");
            MinecraftTransformer.downloadMinecraft(MCVersion, officialJar);

            System.out.println("Downloading mappings file from Quilt...");
            MinecraftTransformer.downloadMappings(MappingsURL, MappingsFile);

            System.out.println("Flattening the Minecraft server jar...");
            MinecraftTransformer.flattenMCJar(officialJar, unpackedJar);

            System.out.println("Remap the Minecraft server jar to use Quilt mappings...");
            MinecraftTransformer.remapMinecraftJar(MappingsFile, unpackedJar, remappedJar);

            System.out.println("Finished all tasks.");
        } else {
            System.out.println("The remapped Minecraft server jar is already present! Delete it and relating files to redownload it!");
        }

        URLClassLoader clsloader = MinecraftTransformer.createClassLoader(remappedJar);

        Class ItemStack = clsloader.loadClass("net.minecraft.item.ItemStack");

        System.out.println(ItemStack);
    }
}
