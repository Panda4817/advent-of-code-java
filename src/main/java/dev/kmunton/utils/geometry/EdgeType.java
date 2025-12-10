package dev.kmunton.utils.geometry;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * Use to count edges to determine if a point is inside or outside a non-regular shape on a grid
 */
@Slf4j
public enum EdgeType {
    LEFT_UP,
    LEFT_DOWN,
    RIGHT_UP,
    RIGHT_DOWN,
    STRAIGHT_VERTICAL,
    STRAIGHT_HORIZONTAL;


    public static EdgeType from(GridPoint edge, Set<GridPoint> edges) {
        GridPoint prevPointX = new GridPoint(edge.x() - 1, edge.y());
        GridPoint nextPointX = new GridPoint(edge.x() + 1, edge.y());
        GridPoint prevPointY = new GridPoint(edge.x(), edge.y() - 1);
        GridPoint nextPointY = new GridPoint(edge.x(), edge.y() + 1);
        if (!edges.contains(prevPointX) && !edges.contains(nextPointX) && edges.contains(prevPointY) && edges.contains(nextPointY)) {
            return STRAIGHT_VERTICAL;
        }
        if (!edges.contains(prevPointY) && !edges.contains(nextPointY) && edges.contains(prevPointX) && edges.contains(nextPointX)) {
            return STRAIGHT_HORIZONTAL;
        }
        if (edges.contains(prevPointX) && edges.contains(nextPointY) && !edges.contains(prevPointY) &&  !edges.contains(nextPointX)) {
            return EdgeType.LEFT_DOWN;
        }
        if (edges.contains(nextPointX) && edges.contains(nextPointY) &&  !edges.contains(prevPointY) && !edges.contains(prevPointX)) {
            return EdgeType.RIGHT_DOWN;
        }
        if (edges.contains(nextPointX) && edges.contains(prevPointY) && !edges.contains(nextPointY) &&  !edges.contains(prevPointX)) {
            return EdgeType.RIGHT_UP;
        }
        if (edges.contains(prevPointX) && edges.contains(prevPointY) && !edges.contains(nextPointX) &&  !edges.contains(nextPointY)) {
            return EdgeType.LEFT_UP;
        }
        log.info("edges contains: prevPointX: {}. nextPointX: {}, prevPointY: {}, nextPointY: {}",
                edges.contains(prevPointX), edges.contains(nextPointX), edges.contains(prevPointY), edges.contains(nextPointY));
        throw new IllegalArgumentException("Not a valid edge: " + edge);
    }

    public static EdgeType getPairedEdge(EdgeType edgeType) {
        return switch (edgeType) {
            case LEFT_UP -> EdgeType.RIGHT_UP;
            case LEFT_DOWN -> EdgeType.RIGHT_DOWN;
            case RIGHT_UP -> EdgeType.LEFT_UP;
            case RIGHT_DOWN -> EdgeType.LEFT_DOWN;
            case STRAIGHT_VERTICAL -> EdgeType.STRAIGHT_VERTICAL;
            default -> EdgeType.STRAIGHT_HORIZONTAL;
        };
    }

}
