package com.github.khanshoaib3.minecraft_access.features;


import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;
import org.platanios.tensorflow.api.core.Graph;
import org.platanios.tensorflow.api.core.Session;
import org.platanios.tensorflow.api.core.Session.Runner;
import org.platanios.tensorflow.api.core.Tensor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotDescription {
    private static final int ACTIVATION_KEY = GLFW.GLFW_KEY_X;  // Change this to the desired activation key

    public static void initialize() {
        MinecraftClient.getInstance().keyboard.setKeyPressedCallback(MinecraftClient.getInstance().window.getHandle(), (window, key, scancode, action, modifiers) -> {
            if (key == ACTIVATION_KEY && action == GLFW.GLFW_PRESS) {
                HitResult hitResult = MinecraftClient.getInstance().crosshairTarget;
                if (hitResult instanceof BlockHitResult) {
                    BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
                    // Capture screenshot
                    String screenshotPath = captureScreenshot();

                    // Generate image description using CNTK
                    String imageDescription = generateImageDescription(screenshotPath);

                    // Print image description in game chat
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(imageDescription));
                }
            }
        });
    }

    private static String captureScreenshot() {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenshot = robot.createScreenCapture(screenRect);

            Path screenshotPath = Files.createTempFile("screenshot", ".png");
            ImageIO.write(screenshot, "png", screenshotPath.toFile());

            return screenshotPath.toString();
        } catch (AWTException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String generateImageDescription(String imagePath) {
        try (Graph graph = new Graph()) {
            byte[] modelBytes = Files.readAllBytes(new ClassPathResource("model.onnx").getFile().toPath());
            graph.importGraphDef(modelBytes);

            try (Session session = new Session(graph)) {
                INDArray imageArray = loadImage(imagePath);
                Tensor<Float> imageTensor = Tensor.create(new long[]{1, 3, 224, 224}, imageArray);

                try (Runner runner = session.runner()) {
                    runner.feed("input_1", imageTensor);
                    runner.fetch("probs/Sigmoid");
                    Tensor<?> outputTensor = runner.runAndFetchMetadata().get(0).getTensor();

                    INDArray outputArray = Nd4j.create(outputTensor.shape());
                    outputTensor.writeTo(outputArray);

                    String imageDescription = processOutput(outputArray);
                    return imageDescription;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static INDArray loadImage(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        BufferedImage resizedImage = resizeImage(image, 224, 224);

        int[] pixels = resizedImage.getRGB(0, 0, 224, 224, null, 0, 224);
        float[] imageArray = new float[pixels.length * 3];

        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            imageArray[i * 3] = ((pixel >> 16) & 0xFF) / 255.0f;
            imageArray[i * 3 + 1] = ((pixel >> 8) & 0xFF) / 255.0f;
            imageArray[i * 3 + 2] = (pixel & 0xFF) / 255.0f;
        }

        return Nd4j.create(imageArray, new int[]{1, 3, 224, 224}, 'c');
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    private static String processOutput(INDArray outputArray) {
        // Process the output array to generate the image description
        // Return the generated image description
        return "";
    }
}
