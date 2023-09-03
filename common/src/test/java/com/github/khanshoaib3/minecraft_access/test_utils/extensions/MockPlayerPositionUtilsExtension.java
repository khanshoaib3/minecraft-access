package com.github.khanshoaib3.minecraft_access.test_utils.extensions;

import com.github.khanshoaib3.minecraft_access.test_utils.ExtensionUtils;
import com.github.khanshoaib3.minecraft_access.test_utils.annotations.MockedStaticPlayerPositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.position.PlayerPositionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.MockedStatic;

import java.util.Objects;
import java.util.Optional;

/**
 * At {@link BeforeTestExecutionCallback} phase, assign new {@link MockedStatic} {@link PlayerPositionUtils} instances to first field that tagged with {@link MockedStaticPlayerPositionUtils}.
 * Close the mocked static instance at {@link AfterTestExecutionCallback} phase.
 */
public class MockPlayerPositionUtilsExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private MockedStatic<PlayerPositionUtils> ms;

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) {
        var o = ExtensionUtils.mockStaticForAnnotatedField(extensionContext, PlayerPositionUtils.class, MockedStaticPlayerPositionUtils.class);
        if (o.isPresent()) {
            this.ms = o.get();
            this.ms.when(PlayerPositionUtils::getPlayerPosition).thenReturn(Optional.of(Vec3d.ZERO));
            this.ms.when(PlayerPositionUtils::getPlayerBlockPosition).thenReturn(Optional.of(new BlockPos(Vec3i.ZERO)));
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) {
        if (Objects.nonNull(this.ms)) this.ms.close();
    }
}
