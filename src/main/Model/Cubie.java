package main.Model;

/**
 * Representation class of a single cubie (Used for indexing of the Model.CubeModel)
 *
 */
public class Cubie {
    // 0-11 for edges, 0-7 for corners
    public byte index;
    //0-1 for edges, 0-2 for corners
    public byte orientation;

    public Cubie(byte index, byte orientation) {
        this.index = index;
        this.orientation = orientation;
    }
    public Cubie(Cubie cub) {
        this.index = cub.index;
        this.orientation = cub.orientation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Cubie other = (Cubie) obj;
        return (this.index == other.index && this.orientation == other.orientation);
    }
}
