#!/usr/bin/env bash
# Vérifie que toute la chaîne d'outils du projet ARES répond.
set -e

echo "=== ARES - vérification de l'environnement ==="
printf "%-12s " "gcc";    gcc --version    | head -1
printf "%-12s " "g++";    g++ --version    | head -1
printf "%-12s " "cmake";  cmake --version  | head -1
printf "%-12s " "java";   java -version 2>&1 | head -1
printf "%-12s " "javac";  javac -version 2>&1 | head -1
printf "%-12s " "maven";  mvn -version 2>&1 | head -1
printf "%-12s " "node";   node --version
printf "%-12s " "tsc";    tsc --version
printf "%-12s " "python"; python3 --version
printf "%-12s " "ps";     ps --version 2>&1 | head -1
 
echo
echo "Environnement prêt."