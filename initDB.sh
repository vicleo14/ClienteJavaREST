#!/bin/bash
sudo mysql < confRoot.sql
mysql -u root -ppractica8 < usuarioNuevo.sql
mysql -u victor -ppractica8 < baseDatos.sql