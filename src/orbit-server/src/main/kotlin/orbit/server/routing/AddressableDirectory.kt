 /*
 Copyright (C) 2015 - 2019 Electronic Arts Inc.  All rights reserved.
 This file is part of the Orbit Project <https://www.orbit.cloud>.
 See license in LICENSE.
 */

package orbit.server.routing

import orbit.server.addressable.AddressableReference
import orbit.server.net.NodeId

 interface AddressableDirectory {
    suspend fun lookup(address: AddressableReference) : NodeId?
    suspend fun setLocation(address: AddressableReference, node: NodeId)
}