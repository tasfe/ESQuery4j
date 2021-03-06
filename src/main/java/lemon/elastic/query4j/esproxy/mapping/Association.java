/*
 * Copyright (c) 2011 by the original author(s).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lemon.elastic.query4j.esproxy.mapping;

/**
 * Value object to capture {@link Association}s.
 * 
 * @param the {@link PersistentProperty}s the association connects.
 * @author Jon Brisbin <jbrisbin@vmware.com>
 */
public class Association<P extends PersistentProperty<P>> {

    private final P inverse;
    private final P obverse;

    /**
     * Creates a new {@link Association} between the two given {@link PersistentProperty}s.
     * 
     * @param inverse
     * @param obverse
     */
    public Association(P inverse, P obverse) {
        this.inverse = inverse;
        this.obverse = obverse;
    }

    public P getInverse() {
        return inverse;
    }

    public P getObverse() {
        return obverse;
    }
}
