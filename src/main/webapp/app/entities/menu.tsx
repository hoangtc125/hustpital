import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/phong-kham">
        <Translate contentKey="global.menu.entities.phongKham" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/chuyen-khoa">
        <Translate contentKey="global.menu.entities.chuyenKhoa" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/countries">
        <Translate contentKey="global.menu.entities.countries" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cities">
        <Translate contentKey="global.menu.entities.cities" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/districts">
        <Translate contentKey="global.menu.entities.districts" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/wards">
        <Translate contentKey="global.menu.entities.wards" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ethnics">
        <Translate contentKey="global.menu.entities.ethnics" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/jobs">
        <Translate contentKey="global.menu.entities.jobs" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bhyt">
        <Translate contentKey="global.menu.entities.bhyt" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/patients">
        <Translate contentKey="global.menu.entities.patients" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/thong-tin-vao-vien">
        <Translate contentKey="global.menu.entities.thongTinVaoVien" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/banks">
        <Translate contentKey="global.menu.entities.banks" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/lich-lam-viec">
        <Translate contentKey="global.menu.entities.lichLamViec" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/doctors">
        <Translate contentKey="global.menu.entities.doctors" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/lich-hen">
        <Translate contentKey="global.menu.entities.lichHen" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
