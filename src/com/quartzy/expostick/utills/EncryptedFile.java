package main.java.ExternalLIBS;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE;

public class EncryptedFile implements Serializable{

    private File file;

    public EncryptedFile(File file, String data) {
        this.file = file;
        saveString(data);
    }

    public EncryptedFile(File file, int data) {
        this.file = file;
        saveInt(data);
    }

    public EncryptedFile(File file, BufferedImage data) {
        this.file = file;
        saveImage(data);
    }

    public EncryptedFile(File file, Object data) {
        this.file = file;
        save(data);
    }

    public EncryptedFile(File file) {
        this.file = file;
    }

    public String getString(){
        StringContainer container = loadStringContainer();
        if (container != null) {
            byte[] value = container.value;
            for (int a = 0;a<value.length;a++){
                value[a] = (byte) (value[a]-a);
            }
            return new String(value);
        }
        return null;
    }

    public BufferedImage getImage(){
        ImageContainer container = Objects.requireNonNull(loadImageContainer());
        int[][] r = container.r;
        int[][] g = container.g;
        int[][] b = container.b;
        int[][] a = container.a;
        BufferedImage img = new BufferedImage(r[0].length, r.length, TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) img.getGraphics();
        graphics.setBackground(new Color(0, 0, 0, 0));
        graphics.clearRect(0, 0, img.getWidth(), img.getHeight());
        for (int y = 0;y<r.length;y++){
            for (int x = 0;x<r[y].length;x++){
                graphics.setColor(new Color(r[y][x], g[y][x], b[y][x], a[y][x]));
                graphics.setPaint(new Color(r[y][x], g[y][x], b[y][x], a[y][x]));
                if (!(a[y][x]==0)) {
                    graphics.drawRect(x, y, 1, 1);
                }
            }
        }
        return img;
    }

    public int getInt(){
        IntegerContainer container = loadIntegerContainer();
        if (container != null) {
            return container.value;
        }
        return 0;
    }

    public Object get(){
        ObjectContainer container = loadObjectContainer();
        if (container != null) {
            return container.value;
        }
        return null;
    }

    public void saveString(String data){
        StringContainer container = new StringContainer();
        byte[] bytes1 = data.getBytes();
        byte[] bytes = new byte[bytes1.length];
        for (int a = 0;a<bytes.length;a++){
            bytes[a] = (byte) (bytes1[a]+a);
        }
        container.value = bytes;
        saveStringContainer(container);
    }

    public void saveInt(int data){
        IntegerContainer container = new IntegerContainer();
        container.value = data;
        saveIntegerContainer(container);
    }

    public void saveImage(BufferedImage data){
        ImageContainer container = new ImageContainer();
        int[][] r = new int[data.getHeight()][data.getWidth()];
        int[][] g = new int[data.getHeight()][data.getWidth()];
        int[][] b = new int[data.getHeight()][data.getWidth()];
        int[][] a = new int[data.getHeight()][data.getWidth()];
        for (int y = 0;y<data.getHeight();y++){
            for (int x = 0;x<data.getWidth();x++){
                Color color = new Color(data.getRGB(x, y), true);
                r[y][x] = color.getRed();
                g[y][x] = color.getGreen();
                b[y][x] = color.getBlue();
                a[y][x] = color.getAlpha();
            }
        }
        container.r = r;
        container.g = g;
        container.b = b;
        container.a = a;
        saveImageContainer(container);
    }

    public void save(Object data){
        ObjectContainer container = new ObjectContainer();
        container.value = data;
        saveObjectContainer(container);
    }

    public void create(){
        try {
            if (!file.exists()){
                File file2 = new File(file.getAbsolutePath().replace(file.getName(), ""));
                file2.mkdirs();
                File file1 = new File(file.getName());
                file1.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveObjectContainer(ObjectContainer data){
        try {
            if (!file.exists()){
                File file2 = new File(file.getAbsolutePath().replace(file.getName(), ""));
                file2.mkdirs();
                File file1 = new File(file.getName());
                file1.createNewFile();
            }
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(data);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObjectContainer loadObjectContainer(){
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            return (ObjectContainer) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveIntegerContainer(IntegerContainer data){
        try {
            if (!file.exists()){
                File file2 = new File(file.getAbsolutePath().replace(file.getName(), ""));
                file2.mkdirs();
                File file1 = new File(file.getName());
                file1.createNewFile();
            }
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(data);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private IntegerContainer loadIntegerContainer(){
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            return (IntegerContainer) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveImageContainer(ImageContainer data){
        try {
            if (!file.exists()){
                File file2 = new File(file.getAbsolutePath().replace(file.getName(), ""));
                file2.mkdirs();
                File file1 = new File(file.getName());
                file1.createNewFile();
            }
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(data);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ImageContainer loadImageContainer(){
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            return (ImageContainer) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveStringContainer(StringContainer data){
        try {
            if (!file.exists()){
                File file2 = new File(file.getAbsolutePath().replace(file.getName(), ""));
                file2.mkdirs();
                File file1 = new File(file.getName());
                file1.createNewFile();
            }
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(data);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringContainer loadStringContainer(){
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            return (StringContainer) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class Container implements Serializable{}

    private class StringContainer extends Container implements Serializable {
        public byte[] value;
    }

    private class ImageContainer extends Container implements Serializable{
        public int[][] r;
        public int[][] g;
        public int[][] b;
        public int[][] a;
    }

    private class IntegerContainer extends Container implements Serializable{
        public int value;
    }

    private class ObjectContainer extends Container implements Serializable{
        public Object value;
    }
}
