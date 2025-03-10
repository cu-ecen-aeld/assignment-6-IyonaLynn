inherit core-image
inherit extrausers

# Add required packages to the image
CORE_IMAGE_EXTRA_INSTALL += "aesd-assignments"
CORE_IMAGE_EXTRA_INSTALL += "openssh"
CORE_IMAGE_EXTRA_INSTALL += "scull misc-modules"

# Set root password (hashed using SHA-256)
# The password hash was generated using:
# printf "%q" $(mkpasswd -m sha256crypt root)
PASSWD = "\$5\$2WoxjAdaC2\$l4aj6Is.EWkD72Vt.byhM5qRtF9HcCM/5YpbxpmvNB5"

EXTRA_USERS_PARAMS = "usermod -p '${PASSWD}' root;"

# Ensure scull and misc-modules are loaded at boot
ROOTFS_POSTPROCESS_COMMAND += "install_init_scripts;"

install_init_scripts() {
    update-rc.d scull defaults 99
    update-rc.d misc-modules defaults 99
}



