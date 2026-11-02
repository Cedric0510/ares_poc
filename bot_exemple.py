#!/usr/bin/env python3

import sys
import json

SHOOT_RANGE = 20.0
SHOOT_AIM = 0.15
MINE_RADIUS = 3.0
DEPOSIT_RADIUS = 6.0
CARGO_MAX = 3


def decide(view):
    me = view["self"]
    enemies = view.get("enemies", [])
    crystals = view.get("crystals", [])
    base = view["base"]

    for enemy in sorted(enemies, key=lambda e: e["distance"]):
        if enemy["distance"] <= SHOOT_RANGE and abs(enemy["direction"]) < SHOOT_AIM:
            return {"action": "SHOOT", "angle": me["heading"] + enemy["direction"]}
    
    if me["cargo"] >= CARGO_MAX:
        if base["distance"] <= DEPOSIT_RADIUS:
            return {"action": "DEPOSIT"}
        return {"action": "MOVE", "angle": me["heading"] + base["direction"]}

    if crystals:
        nearest = min(crystals, key=lambda c: c["distance"])
        if nearest["distance"] <= MINE_RADIUS:
            return {"action": "MINE"}
        return {"action": "MOVE", "angle": me["heading"] + nearest["direction"]}

    return {"action": "MOVE", "angle": me["heading"] + 0.2}


def main():

    while True:
        line = sys.stdin.readline()
        if not line:
            break
        line = line.strip()
        if not line:
            continue

        try:
            view = json.loads(line)
        except json.JSONDecodeError:
            print(json.dumps({"action": "WAIT"}), flush=True)
            continue

        action = decide(view)
        print(json.dumps(action), flush=True)


if __name__ == "__main__":
    main()