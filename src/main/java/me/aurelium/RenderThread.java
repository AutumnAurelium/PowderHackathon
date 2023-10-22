package me.aurelium;

import me.aurelium.particles.*;
import me.aurelium.particles.SuperParticle.Particle;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL12;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.locks.ReentrantLock;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class RenderThread {
    public static final int WIN_WIDTH = 512;
    public static final int WIN_HEIGHT = 512;
    public static final int SIM_SIZE = 128;
    public static final int SIZE_RATIO = WIN_WIDTH / SIM_SIZE;

    private final int[] pixColors = new int[SIM_SIZE * SIM_SIZE];
    private final ReentrantLock pixColorsLock = new ReentrantLock();
    private boolean shouldExit = false;

    // The window handle
    private long window;

    private int selected = 0;
    private static final int maxSelected = 7;

    public int[] lockPixelArray() {
        pixColorsLock.lock();
        return pixColors;
    }

    public void unlockPixelArray() {
        pixColorsLock.unlock();
    }

    public boolean shouldExit() {
        return shouldExit;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private Particle createFromSelected() {
        switch (selected) {
            case 0: return new Dust();
            case 1: return new Metal();
            case 2: return new Water();
            case 3: return new Grass();
            case 4: return new Fire();
            case 5: return new Stone();
            case 6: return new Acid();
            case 7: return new Magma();
            default: return new Dust();
        }
    }

    private String nameFromSelected() {
        switch(selected) {
            case 0: return "Dust";
            case 1: return "Metal";
            case 2: return "Water";
            case 3: return "Grass";
            case 4: return "Fire";
            case 5: return "Stone";
            case 6: return "Acid";
            case 7: return "Magma";
            default: return "";
        }
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(WIN_WIDTH, WIN_HEIGHT, "Powder", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            if(key == GLFW_KEY_R && action == GLFW_RELEASE) {
                if((mods & GLFW_MOD_SHIFT) != 0) {
                    if(selected == 0) {
                        selected = maxSelected;
                    } else {
                        selected--;
                    }
                } else {
                    if (selected == maxSelected) {
                        selected = 0;
                    } else {
                        selected++;
                    }
                }
            }
        });

        glfwSetCursorPosCallback(window, (window, xPos, yPos) -> {
            int simX = (int) xPos / SIZE_RATIO;
            int simY = (int) yPos / SIZE_RATIO;
            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) != GLFW_RELEASE) {
                for (int x = simX - 5; x < simX + 5; x++) {
                    for (int y = simY - 5; y < simY + 5; y++) {
                        if (x > 0 && y > 0 && x < SIM_SIZE && y < SIM_SIZE) {
                            gameThread.setParticle(x, y, createFromSelected());
                        }
                    }
                }
            } else if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) != GLFW_RELEASE) {
                for (int x = simX - 5; x < simX + 5; x++) {
                    for (int y = simY - 5; y < simY + 5; y++) {
                        if (x > 0 && y > 0 && x < SIM_SIZE && y < SIM_SIZE) {
                            gameThread.setParticle(x, y, new Air());
                        }
                    }
                }
            }
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, SIM_SIZE, SIM_SIZE, 0.0, -1.0, 1.0);

        glMatrixMode(GL_MODELVIEW);

        ByteBuffer buffer = BufferUtils.createByteBuffer(SIM_SIZE * SIM_SIZE * 4);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSetWindowTitle(window, "Powder (" + nameFromSelected() + ")");

            for(int y = 0; y < SIM_SIZE; y++) {
                for(int x = 0; x < SIM_SIZE; x++) {
                    int pix = pixColors[x + y * SIM_SIZE];

                    if(x < 10 && y > SIM_SIZE-3) // show selected
                        pix = createFromSelected().getColor().getRGB();

                    buffer.put((byte)(pix >> 16 & 0xFF));
                    buffer.put((byte)(pix >> 8 & 0xFF));
                    buffer.put((byte)(pix & 0xFF));
                    buffer.put((byte)255);
                }
            }

            buffer.flip();

            int textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

            //Setup texture scaling filtering
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, SIM_SIZE, SIM_SIZE, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            glEnable(GL_BLEND);
            glEnable(GL_TEXTURE_2D);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            glPushMatrix();
            glTranslatef(0, 0, 0);
            glBindTexture(GL_TEXTURE_2D, textureID);
            glBegin(GL_QUADS); // BEGIN

            glTexCoord2f(0, 0);
            glVertex2f(0, 0);

            glTexCoord2f(1, 0);
            glVertex2f(SIM_SIZE, 0);

            glTexCoord2f(1, 1);
            glVertex2f(SIM_SIZE, SIM_SIZE);

            glTexCoord2f(0, 1);
            glVertex2f(0, SIM_SIZE);

            glEnd(); // END
            glPopMatrix();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

        shouldExit = true;
    }

    private static GameThread gameThread;

    public static void main(String[] args) {
        RenderThread mainThread = new RenderThread();
        gameThread = new GameThread(mainThread);
        gameThread.start();
        mainThread.run();
    }

}
