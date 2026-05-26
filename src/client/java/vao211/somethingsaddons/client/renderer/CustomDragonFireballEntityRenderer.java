package vao211.somethingsaddons.client.renderer;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;
import vao211.somethingsaddons.entity.CustomDragonFireballEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;

public class CustomDragonFireballEntityRenderer extends EntityRenderer<CustomDragonFireballEntity> {
    private static final Identifier LAVA_TEXTURE = Identifier.of("minecraft", "textures/block/lava_still.png");
    public CustomDragonFireballEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CustomDragonFireballEntity entity) {
        return LAVA_TEXTURE;
    }

    @Override
    public void render(CustomDragonFireballEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        float scale = 2.0F;
        matrices.scale(scale,scale,scale);

        float ticks = (float) entity.age + tickDelta;
        matrices.multiply(new Quaternionf().rotationXYZ(ticks * 0.06F, ticks * 0.06F, ticks * 0.03F));

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(this.getTexture(entity)));
        drawCube(matrices, vertexConsumer, light);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private void drawCube(MatrixStack matrices, VertexConsumer vertexConsumer, int light) {
        MatrixStack.Entry entry = matrices.peek();
        float min = -0.2F;
        float max = 0.2F;

        vertex(entry, vertexConsumer, min, min, max, 0, 1, 0, 0, 1, light);
        vertex(entry, vertexConsumer, max, min, max, 1, 1, 0, 0, 1, light);
        vertex(entry, vertexConsumer, max, max, max, 1, 0, 0, 0, 1, light);
        vertex(entry, vertexConsumer, min, max, max, 0, 0, 0, 0, 1, light);

        vertex(entry, vertexConsumer, min, min, min, 0, 1, 0, 0, -1, light);
        vertex(entry, vertexConsumer, min, max, min, 0, 0, 0, 0, -1, light);
        vertex(entry, vertexConsumer, max, max, min, 1, 0, 0, 0, -1, light);
        vertex(entry, vertexConsumer, max, min, min, 1, 1, 0, 0, -1, light);

        vertex(entry, vertexConsumer, min, max, min, 0, 0, 0, 1, 0, light);
        vertex(entry, vertexConsumer, min, max, max, 0, 1, 0, 1, 0, light);
        vertex(entry, vertexConsumer, max, max, max, 1, 1, 0, 1, 0, light);
        vertex(entry, vertexConsumer, max, max, min, 1, 0, 0, 1, 0, light);

        vertex(entry, vertexConsumer, min, min, min, 0, 0, 0, -1, 0, light);
        vertex(entry, vertexConsumer, max, min, min, 1, 0, 0, -1, 0, light);
        vertex(entry, vertexConsumer, max, min, max, 1, 1, 0, -1, 0, light);
        vertex(entry, vertexConsumer, min, min, max, 0, 1, 0, -1, 0, light);

        vertex(entry, vertexConsumer, max, min, min, 0, 1, 1, 0, 0, light);
        vertex(entry, vertexConsumer, max, max, min, 0, 0, 1, 0, 0, light);
        vertex(entry, vertexConsumer, max, max, max, 1, 0, 1, 0, 0, light);
        vertex(entry, vertexConsumer, max, min, max, 1, 1, 1, 0, 0, light);

        vertex(entry, vertexConsumer, min, min, min, 0, 1, -1, 0, 0, light);
        vertex(entry, vertexConsumer, min, min, max, 1, 1, -1, 0, 0, light);
        vertex(entry, vertexConsumer, min, max, max, 1, 0, -1, 0, 0, light);
        vertex(entry, vertexConsumer, min, max, min, 0, 0, -1, 0, 0, light);
    }

    private void vertex(MatrixStack.Entry matrixEntry, VertexConsumer vertexConsumer, float x, float y, float z, float u, float v, float normalX, float normalY, float normalZ, int light) {
        vertexConsumer.vertex(matrixEntry.getPositionMatrix(), x, y, z)
                .color(255, 255, 255, 255)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(matrixEntry, normalX, normalY, normalZ);
    }
}