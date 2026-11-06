type MatchStartedEvent = {
    seq: number;
    type: "MATCH_STARTED";
    robots: string[];
};

type RobotMovedEvent = {
    seq: number;
    type: "ROBOT_MOVED";
    robot: string;
    x: number;
    y: number;
};

type RobotShotEvent = {
    seq: number;
    type: "ROBOT_SHOT";
    robot: string;
    hit: string;
};

type RobotDamagedEvent = {
    seq: number;
    type: "ROBOT_DAMAGED";
    robot: string;
    hp: number;
    alive: boolean;
};

export type MatchEvent = MatchStartedEvent | RobotMovedEvent | RobotShotEvent | RobotDamagedEvent;

export type RobotState = {
    x: number;
    y: number;
    hp: number;
    alive: boolean;
};

export type MatchState = {
    [robotId: string]: RobotState;
};

export function createInitialState(event: MatchStartedEvent): MatchState {
    const state: MatchState = {};

    for (const robotId of event.robots) {
        state[robotId] = {
            x: 0,
            y: 0,
            hp: 100,
            alive: true
        };
    }

    return state;
}
