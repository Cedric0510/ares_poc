FROM debian:bookworm-slim

ENV DEBIAN_FRONTEND=noninteractive \
    LANG=C.UTF-8 \
    LC_ALL=C.UTF-8

# --- Outillage de base + chaîne C/C++ --------------------------------------
RUN apt-get update && apt-get install -y --no-install-recommends \
      build-essential \
      gcc \
      g++ \
      gdb \
      cmake \
      ninja-build \
      pkg-config \
      git \
      curl \
      ca-certificates \
      procps \
      bash \
      unzip \
      python3 \
      python3-pip \
      python3-venv \
    && rm -rf /var/lib/apt/lists/*

# --- Java 21 (Eclipse Temurin, archive directe) ----------------------------
RUN ARCH="$(dpkg --print-architecture)" \
    && case "$ARCH" in amd64) A=x64 ;; arm64) A=aarch64 ;; *) exit 1 ;; esac \
    && curl -fsSL -o /tmp/jdk.tar.gz \
       "https://api.adoptium.net/v3/binary/latest/21/ga/linux/${A}/jdk/hotspot/normal/eclipse" \
    && mkdir -p /opt/java-21 \
    && tar -xzf /tmp/jdk.tar.gz -C /opt/java-21 --strip-components=1 \
    && rm /tmp/jdk.tar.gz

ENV JAVA_HOME=/opt/java-21 \
    PATH="/opt/java-21/bin:${PATH}"

RUN apt-get update && apt-get install -y --no-install-recommends maven \
    && rm -rf /var/lib/apt/lists/*

# --- Node.js 20 LTS + TypeScript (archive directe) -------------------------
RUN ARCH="$(dpkg --print-architecture)" \
    && case "$ARCH" in amd64) A=x64 ;; arm64) A=arm64 ;; *) exit 1 ;; esac \
    && curl -fsSL -o /tmp/node.tar.xz \
       "https://nodejs.org/dist/v20.18.0/node-v20.18.0-linux-${A}.tar.xz" \
    && tar -xJf /tmp/node.tar.xz -C /usr/local --strip-components=1 \
    && rm /tmp/node.tar.xz \
    && npm install -g typescript@5.6.3 ts-node@10.9.2