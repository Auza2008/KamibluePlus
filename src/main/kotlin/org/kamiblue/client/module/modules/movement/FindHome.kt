package org.kamiblue.client.module.modules.movement

import kotlinx.coroutines.delay
import org.kamiblue.client.event.events.PlayerMoveEvent
import org.kamiblue.client.module.Category
import org.kamiblue.client.module.Module
import org.kamiblue.client.module.modules.movement.ElytraFlight.setting
import org.kamiblue.client.module.modules.movement.Step.setting
import org.kamiblue.client.util.InfoCalculator.speed
import org.kamiblue.client.util.MSTimer
import org.kamiblue.client.util.TimeUtils
import org.kamiblue.client.util.threads.safeListener
import java.util.concurrent.TimeUnit

internal object FindHome : Module(name = "FindHome", description = "only work when you set speed is 2.", category = Category.MOVEMENT) {
    //哼哼~啊啊啊啊啊啊啊啊！！！！！

    private var i = 0
    private var time = MSTimer()
    private var time2 = MSTimer()

    init {
        onEnable {
            mc.player.rotationYaw = 0F
            mc.player.rotationYawHead = 0F
            time.reset()
        }
        safeListener<PlayerMoveEvent> {
            if (time.hasTimePassed(24000L)) {
                repeat(1) {
                    i = 90
                    mc.player.rotationYaw = 0F + i
                    mc.player.rotationYawHead = 0F + i
                }
                time.reset()
            }

            if (time2.hasTimePassed(72100L)) {
                time.reset()
                time2.reset()
            }
        }
    }
}