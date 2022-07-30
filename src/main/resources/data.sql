insert into unit(id, name, abbreviation, parent_Unit_Id, director_Id) values(1, 'Example Corporation', 'EC', null, null);
insert into unit(id, name, abbreviation, parent_Unit_Id, director_Id) values(2, 'Management & Administration', 'MA', 1, null);
insert into unit(id, name, abbreviation, parent_Unit_Id, director_Id) values(3, 'Engineering & Production', 'EP', 1, null);
insert into unit(id, name, abbreviation, parent_Unit_Id, director_Id) values(4, 'Research & Development', 'RD', 1, null);
insert into unit(id, name, abbreviation, parent_Unit_Id, director_Id) values(5, 'Research & Development: Training', 'RDT', 4, null);
insert into unit(id, name, abbreviation, parent_Unit_Id, director_Id) values(6, 'Research & Development: Innovation', 'RDI', 4, null);
insert into unit(id, name, abbreviation, parent_Unit_Id, director_Id) values(7, 'Research & Development: Elaboration', 'RDE', 4, null);
insert into unit(id, name, abbreviation, parent_Unit_Id, director_Id) values(8, 'Research & Development: Publication', 'RDP', 4, null);


-- unit 1 EC
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(1, 'Berry Boss', 'BB', 'Director', null, 1);
update unit set director_Id = 1 where id = 1;
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(2, 'George Wolf', 'GW', 'Assistent', 1, 1);

-- unit 2 MA
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(3, 'Otto Normal', 'ON', 'Consultant', 1, 2);
update unit set director_Id = 3 where id = 2;
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(4, 'Martha Müller', 'MM', 'Consultant', 3, 2);
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(5, 'Matthew Klein', 'MK', 'Assistent', 3, 2);

-- unit 3 EP
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(6, 'Clara Schmidt', 'CS', 'Architect', 1, 3);
update unit set director_Id = 6 where id = 3;
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(7, 'Michael Weber', 'MW', 'Engineer', 6, 3);
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(8, 'Sarah Meyer', 'SM', 'Engineer', 6, 3);
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(9, 'David Wagner', 'DW', 'Engineer', 6, 3);
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(10, 'Richard Becker', 'RB', 'Engineer', 6, 3);

-- unit 4 RD
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(11, 'Robert Fischer', 'RF', 'Architect', 1, 4);
update unit set director_Id = 11 where id = 4;
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(12, 'Joseph Schulz', 'JS', 'Engineer', 11, 4);
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(13, 'Alice Schäfer', 'AS', 'Engineer', 11, 4);
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(14, 'Daniel Richter', 'DR', 'Assistent', 11, 4);

-- unit 5 RDT
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(15, 'Thomas Koch', 'TK', 'Trainee', 1, 5);
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(16, 'Christopher Bauer', 'CB', 'Trainee', 1, 5);

-- unit 6 RDI
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(17, 'Timothy Beck', 'TB', 'Architect', 1, 6);
update unit set director_Id = 17 where id = 6;
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(18, 'Margaret Lorenz', 'ML', 'Engineer', 17, 6);
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(19, 'Jeffrey Baumann', 'JB', 'Engineer', 17, 6);

-- unit 7 RDE
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(20, 'Elizabeth Franke', 'EF', 'Architect', 1, 7);
update unit set director_Id = 20 where id = 7;
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(21, 'Gary Albrecht', 'GA', 'Engineer', 20, 7);
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(22, 'Ryan Schuster', 'RS', 'Engineer', 20, 7);
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(23, 'Emma Simon', 'ES', 'Engineer', 20, 7);
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(24, 'Eric Ludwig', 'EL', 'Engineer', 20, 7);

-- unit 8 RDP
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(25, 'Stephen Böhm', 'SB', 'Architect', 1, 8);
update unit set director_Id = 25 where id = 8;
insert into person(id, name, initials, role, supervisor_Id, belongs_To_Id) values(26, 'Mary Winter', 'MW', 'Engineer', 25, 8);