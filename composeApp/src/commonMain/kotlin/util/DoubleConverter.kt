package util

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter

// animation converter for double values
class DoubleConverter : TwoWayConverter<Double, AnimationVector1D> {
    override val convertFromVector: (AnimationVector1D) -> Double
        get() = { vector -> vector.value.toDouble() }

    override val convertToVector: (Double) -> AnimationVector1D
        get() = { value -> AnimationVector1D(value.toFloat()) }
}
