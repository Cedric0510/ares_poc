#!/usr/bin/env python3

import signal

signal.signal(signal.SIGTERM, signal.SIG_IGN)

while True:
    pass