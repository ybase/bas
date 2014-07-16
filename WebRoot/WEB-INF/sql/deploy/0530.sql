use dormitory;
alter table dr_talk add yescount numeric(10);
alter table dr_talk add nocount numeric(10);
update dr_talk set yescount=0,nocount=0;