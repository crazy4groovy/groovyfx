/*
* Copyright 2011 the original author or authors.
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

package groovyx.javafx.factory.animation
import javafx.animation.*;
import javafx.beans.value.WritableValue;
import groovyx.javafx.animation.TargetHolder
import groovyx.javafx.factory.AbstractGroovyFXFactory;

/**
 *
 * @author jimclarke
 */

class KeyValueFactory extends AbstractGroovyFXFactory {
    public static final TARGET_HOLDERS_PROPERTY = "__target_holders"
    
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        if(value instanceof List && value.size() == 2) {
            return new TargetHolder(bean: value.get(0), propertyName: value.get(1));
        }else if(value instanceof WritableValue) {
            return new TargetHolder(property: value);
        }else {
            return null;
        }
    }

    public void setChild(FactoryBuilderSupport build, Object parent, Object child) {
        if(parent instanceof TargetHolder) {
            if(child instanceof Interpolator) {
                ((TargetHolder)parent).interpolator = (Interpolator)child;
            }else  {
                ((TargetHolder)parent).endValue = child;
            }
        }
    }

    public void onNodeCompleted( FactoryBuilderSupport builder, Object parent, Object node )  {
        if(node instanceof TargetHolder) {
            def keyValues = builder.parentContext.get(TARGET_HOLDERS_PROPERTY, [])
            keyValues << node
        }
    }
}

