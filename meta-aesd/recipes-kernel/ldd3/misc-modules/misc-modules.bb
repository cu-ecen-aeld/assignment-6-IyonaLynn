# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this with the path to your assignments repo. Use ssh protocol
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-IyonaLynn.git;protocol=ssh;branch=main"
SRC_URI += "file://misc-init"

PV = "1.0+git${SRCPV}"
# TODO: Set to reference a specific commit hash in your assignment repo
SRCREV = "edfa61fa7a87bc9f3006810556b6afd6746eb055"

# Set staging directory
S = "${WORKDIR}/git/misc-modules"

# Kernel module compilation
inherit module
inherit update-rc.d

INITSCRIPT_NAME = "misc-modules"
INITSCRIPT_PARAMS = "defaults 90"

do_configure () {
    :
}

do_compile () {
    oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules
}

do_install () {
    oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules_install INSTALL_MOD_PATH=${D}

    # Ensure the directory for kernel modules exists
    install -d ${D}/lib/modules/${KERNEL_VERSION}/extra/
}

do_install:append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/misc-init ${D}${sysconfdir}/init.d/misc-modules
}

# Define installed files
FILES:${PN} += "/lib/modules/${KERNEL_VERSION}/extra/misc-modules.ko"
FILES:${PN} += "${sysconfdir}/init.d/misc-modules"

RPROVIDES:${PN} += "kernel-module-misc-modules"

