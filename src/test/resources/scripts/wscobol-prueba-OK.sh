#!/usr/bin/env bash
FILE_TEMP=$1
echo "Desde dentro del shell script..."
echo -e "OK\nRESULTADO-INICIO\nresultado1\nresultado2\nresultado3\nRESULTADO-FIN" > $FILE_TEMP
echo "fichero $FILE_TEMP actualizado correctamente..."