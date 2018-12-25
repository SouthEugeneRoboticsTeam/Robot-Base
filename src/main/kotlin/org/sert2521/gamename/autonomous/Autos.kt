package org.sert2521.gamename.autonomous

import org.sert2521.gamename.drivetrain.followPath

/**
 * This file defines the various different auto modes that can be run. They should call commands and
 * consist of minimal logic, instead deferring to their commands to handle any logic that might be
 * required.
 */

suspend fun testStraightAuto() {
    val auto = autonomi["Tests"]

    val path = auto["8 Foot Straight"]

    try {
        followPath(path, 0.5)
    } finally {
        println("Done following path")
    }
}
