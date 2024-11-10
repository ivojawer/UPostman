package domain.response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageResponse implements Response {
    private BufferedImage body;

    public ImageResponse(InputStream body) throws IOException {
        this.body = ImageIO.read(body);
    }

    @Override
    public Boolean isText() {
        return false;
    }

    @Override
    public Boolean isImage() {
        return true;
    }

    public BufferedImage getImage() {
        return body;
    }
}
