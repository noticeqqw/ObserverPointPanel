# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java Swing application implementing a custom JPanel component with Observer pattern for displaying points on a 2D coordinate plane. It's a lab assignment (ЛР 3) demonstrating GUI component development with dynamic behavior.

## Architecture

### Observer Pattern Implementation

The application uses the classic Observer/Observable pattern:

- **Observer** interface: Defines `update(double x, double y)` method for receiving point notifications
- **Observable** class: Manages observer list and generates random points within configurable ranges
- **PointsPanel**: Extends JPanel and implements Observer, displaying N most recent points

### Key Components

**PointsPanel** (`panel/points/PointsPanel.java`)
- Extends JPanel and implements Observer interface
- Maintains a bounded list of Point2D.Double objects (max N points)
- Automatically removes oldest point when capacity exceeded
- Coordinate transformation: logical coordinates → screen coordinates
- Thread-safe point storage using synchronized blocks
- Methods:
  - `setVal(x, y)`: Direct point addition
  - `setRange(minX, maxX, minY, maxY)`: Runtime coordinate range modification
  - `getPoints()`: Returns unmodifiable view of accumulated points
  - `update(x, y)`: Observer pattern callback (delegates to setVal)

**Observable** (`panel/points/Observable.java`)
- Manages List<Observer> for notifications
- `generatePoint()`: Creates random points within current range
- `notifyObservers(x, y)`: Broadcasts to all observers
- Range is mutable via `setRange()`

### UI Design

The Main class creates:
- Single PointsPanel with initial range [0,100] for both X and Y
- Control buttons for manual/automatic point generation
- Timer-based auto-generation (1 second interval)
- Dynamic range modification demonstration

All UI text is in Russian with emoji prefixes for visual clarity.

## Code Conventions

- Package: `panel.points`
- Java version: 17
- UI text: Russian language with emoji icons
- Coordinate system: Standard mathematical (Y increases upward, transformed for screen)
- Point visualization: Small filled circles (6px diameter)

- Не запускай проект, только изменяй