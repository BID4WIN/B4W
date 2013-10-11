mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_FOO_CHILD.SQL
mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_FOO_EMBEDDED_LIST.SQL
mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_FOO_EMBEDDED_SET.SQL
mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_FOO.SQL

mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_ACCOUNT_BASED_SINGLE.SQL
mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_ACCOUNT_BASED_MULTIPLE.SQL
mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_CONNECTION_STUB.SQL
mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_CONNECTION_HISTORY_STUB.SQL
mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_PASSWORD_REINIT_STUB.SQL
mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_ACCOUNT_STUB.SQL
mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_RESOURCE_USAGE_STUB.SQL
mysql --host=localhost --user=bid4win --password=bid4win bid4win_db_test < ./DOWNGRADE_2_V_0_0_0/TABLE_RESOURCE_STORAGE_STUB.SQL
