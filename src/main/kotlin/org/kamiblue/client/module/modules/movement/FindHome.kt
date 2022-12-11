package org.kamiblue.client.module.modules.movement

import baritone.api.event.events.TickEvent
import net.minecraft.util.MovementInputFromOptions
import net.minecraftforge.client.event.InputUpdateEvent
import org.kamiblue.client.event.events.PlayerMoveEvent
import org.kamiblue.client.module.Category
import org.kamiblue.client.module.Module
import org.kamiblue.client.module.modules.player.LagNotifier
import org.kamiblue.client.module.modules.player.ViewLock
import org.kamiblue.client.util.MSTimer
import org.kamiblue.client.util.threads.safeListener
import org.kamiblue.event.listener.listener

internal object FindHome : Module(name = "FindHome", description = "Find a base(Maybe)", category = Category.MOVEMENT) {
    //哼哼~啊啊啊啊啊啊啊啊！！！！！
    private var walkMode by setting("AutoWalk", true)
    private var speed by setting("RotSpeed", 2, 1..5, 1)

    private var i = 90
    private var time = MSTimer()
    private var time2 = MSTimer()
    private var yawSnap = 0

    init {
        onEnable {
            time.reset()
            time2.reset()
        }
        listener<net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent> {
            if (!ElytraFlight.isEnabled || ElytraFlight.isEnabled && mc.player.onGround) {
                time.reset()
                time2.reset()
            }
        }
        safeListener<PlayerMoveEvent> {
            var timeOutSpeed = ((48 / speed) * 1000)
            var resetSpeed = ((timeOutSpeed * 3) + 100)
            if (time.hasTimePassed(timeOutSpeed.toLong())) {
                mc.player.rotationYaw = mc.player.rotationYaw + i
                mc.player.rotationYawHead = mc.player.rotationYawHead + i
                time.reset()
            }

            if (time2.hasTimePassed(resetSpeed.toLong())) {
                time.reset()
                time2.reset()
            }
        }
        listener<InputUpdateEvent>(6969) {
            if (walkMode) {
                if (LagNotifier.paused && LagNotifier.pauseAutoWalk) return@listener

                if (it.movementInput !is MovementInputFromOptions) return@listener
                it.movementInput.moveForward = 1.0f
            }
        }
    }
}