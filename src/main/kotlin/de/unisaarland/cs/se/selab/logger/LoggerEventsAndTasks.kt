package de.unisaarland.cs.se.selab.logger

import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.tasks.Reward
import de.unisaarland.cs.se.selab.tasks.Task
/** Log Events and Tasks*/
object LoggerEventsAndTasks {
    /** Log the start of an event. */
    fun logEventStart(eventId: Int, eventType: Event) {
        Logger.log("Event: Event $eventId of type $eventType happened.")
    }

    /** Log the start of a task. */
    fun logTaskStart(taskId: Int, taskType: String, shipID: Int, tileId: Int) {
        Logger.log(
            "Task: Task $taskId of type $taskType with ship $shipID is added with destination $tileId."
        )
    }

    /** Log a task being added to a ship. */
    fun logTaskAddedToShip(taskId: Int, taskType: Task, shipId: Int, tileId: Int) {
        Logger.log("Task: Task $taskId of type $taskType with ship $shipId is added with destination $tileId.")
    }

    /** Log a reward being received. */
    fun logRewardReceived(taskId: Int, shipId: Int, rewardType: Reward) {
        Logger.log("Reward: Task $taskId: Ship $shipId received reward of type $rewardType.")
    }
}
