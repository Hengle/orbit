/*
 Copyright (C) 2015 - 2019 Electronic Arts Inc.  All rights reserved.
 This file is part of the Orbit Project <https://www.orbit.cloud>.
 See license in LICENSE.
 */

package orbit.server.mesh

import java.time.Duration

data class LeaseDuration(val leaseDuration: Long) {
    val expiresIn: Duration get() = Duration.ofSeconds(leaseDuration)
    val renewIn: Duration get() = Duration.ofSeconds(leaseDuration / 2)
}