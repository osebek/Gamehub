INSERT INTO game_info (id,name,description,difficulty_levels) VALUES
	 ('79dcf505-d4b6-47f2-a599-8447cb1c26d9'::uuid,'Game 1','Description for game 1','{NORMAL,GOD}');
INSERT INTO game_info (id,name,description,difficulty_levels) VALUES
	 ('f5c1daa7-6267-4bf7-b867-4ed0ea2a6f10'::uuid,'Game 2','Description for game 2','{NORMAL}');

INSERT INTO game_session (id,game_info_id,player_id,create_timestamp,status,difficulty,"configuration",current_state) VALUES
	 ('b1d4527d-bce7-41d2-a1a8-7f7b276398e6'::uuid,'79dcf505-d4b6-47f2-a599-8447cb1c26d9'::uuid,'dd84a735-0cf2-4bee-b094-e6fcf4f98dd9'::uuid,'2025-08-30 21:57:23.957','OPEN','GOD','{}','{}');